package com.example.mojapotrosnja.utils

import android.app.Activity
import com.example.mojapotrosnja.common.BaseObservable
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionManager(
    private val activity: Activity?
): BaseObservable<PermissionManager.Listener>() {

    interface Listener{
        fun permissionsGranted()
        fun permissionNotGranted()
    }

    fun requestPermissions(
        list: ArrayList<String>
    ) {
        Dexter.withActivity(activity)
            .withPermissions(list)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (!report!!.areAllPermissionsGranted()) {
                        listeners.forEach {
                            it.permissionNotGranted()
                        }
                    } else {
                        listeners.forEach {
                            it.permissionsGranted()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

            }).check()
    }
}