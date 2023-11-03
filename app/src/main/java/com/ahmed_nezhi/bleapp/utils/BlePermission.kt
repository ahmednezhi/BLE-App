package com.ahmed_nezhi.bleapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Create by A.Nezhi on 03/11/2023.
 */
object BlePermission {

    fun hasBlePermissions(context: Context): Boolean {
        val isBlePermissionGranted = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH
        ) == PackageManager.PERMISSION_GRANTED

        val isBleAdminPermissionGranted = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_ADMIN
        ) == PackageManager.PERMISSION_GRANTED

        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        // For API level 31 and above, check for BLUETOOTH_CONNECT and BLUETOOTH_ADVERTISE
        val isBleConnectPermissionGranted = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // Permission not needed before API 31
            true
        }

        val isBleAdvertisePermissionGranted = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_ADVERTISE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // Permission not needed before API 31
            true
        }

        return isBlePermissionGranted && isBleAdminPermissionGranted && isLocationPermissionGranted &&
                isBleConnectPermissionGranted && isBleAdvertisePermissionGranted
    }

    fun getNeededPermissions(context: Context): List<String> {
        val permissions = mutableListOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        // For Android 12 (API level 31) and above, BLUETOOTH_CONNECT and BLUETOOTH_ADVERTISE permissions are required
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            permissions.add(Manifest.permission.BLUETOOTH_ADVERTISE)
        }

        return permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
    }
}