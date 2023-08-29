package com.example.throw_fornt.feature.home

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
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
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setupViewModel()
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
