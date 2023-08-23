package com.example.throw_fornt.feature.home

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityHomeBinding
import com.example.throw_fornt.feature.map.MapFragment
import com.example.throw_fornt.feature.myPage.MyPageFragment
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        if (checkLocationService().not()) {
            Toaster.showShort(this, "GPS를 먼저 켜주세요.")
            finish()
        }

        TedPermission.create()
            .setPermissionListener(
                object : PermissionListener {
                    override fun onPermissionGranted() {
                        Toaster.showShort(this@HomeActivity, "권한을 얻기 성공")
                        setupViewModel()
                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                        Toaster.showShort(this@HomeActivity, "권한을 받아오지 못했습니다.")
                        finish()
                    }
                },
            ).setDeniedMessage("위치 권한이 없으면 앱을 사용할 수 없습니다.\n\n[설정]->[권한]->[위치]->[항상 허용]")
            .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
        getHashKey() // 여기서 로그로 나온 디버그용 해시키 값을 카카오 콘솔에 등록할 것
    }

    private fun setupViewModel() {
        viewModel.currentFragmentType.observe(this) {
            changeFragment(it)
        }
    }

    private fun changeFragment(fragmentType: FragmentType) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            supportFragmentManager.fragments.forEach(::hide)
            supportFragmentManager.findFragmentByTag(fragmentType.tag)?.let {
                show(it)
            } ?: run { add(R.id.fcv_container, createFragment(fragmentType), fragmentType.tag) }
        }
    }

    private fun createFragment(fragmentType: FragmentType): Fragment {
        return when (fragmentType) {
            FragmentType.MAP -> MapFragment()
            FragmentType.MY_PAGE -> MyPageFragment()
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}
