package com.example.throw_fornt.util.common

import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

fun Context.requestTedPermission(
    permissions: List<String>,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    deniedMessage: String = "설정에서 권한을 허락해주세요",
) {
    TedPermission.create()
        .setPermissionListener(
            object : PermissionListener {
                override fun onPermissionGranted() {
                    Toaster.showShort(this@requestTedPermission, "권한을 얻기 성공")
                    onPermissionGranted()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toaster.showShort(this@requestTedPermission, "권한을 받아오지 못했습니다.")
                    onPermissionDenied()
                }
            },
        ).setDeniedMessage(deniedMessage)
        .setPermissions(*permissions.toTypedArray())
        .check()
}
