package com.ahmed_nezhi.bleapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.ahmed_nezhi.bleapp.utils.BlePermission
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BlePermissionTest {

    private val context = mockk<Context>(relaxed = true)

    @Before
    fun setup() {
        mockkStatic(ContextCompat::class)
    }

    @Test
    fun `hasBlePermissions returns true when all permissions are granted`() {
        every {
            ContextCompat.checkSelfPermission(
                any(),
                any()
            )
        } returns PackageManager.PERMISSION_GRANTED

        val result = BlePermission.hasBlePermissions(context)

        assertTrue(result)
    }

    @Test
    fun `hasBlePermissions returns false when any permission is not granted`() {
        every {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH
            )
        } returns PackageManager.PERMISSION_GRANTED
        every {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        } returns PackageManager.PERMISSION_GRANTED
        every {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } returns PackageManager.PERMISSION_DENIED // Simulate a denied permission

        val result = BlePermission.hasBlePermissions(context)

        assertFalse(result)
    }

    @Test
    fun `getNeededPermissions returns empty list when all permissions are granted`() {
        every {
            ContextCompat.checkSelfPermission(
                any(),
                any()
            )
        } returns PackageManager.PERMISSION_GRANTED

        val neededPermissions = BlePermission.getNeededPermissions(context)

        assertTrue(neededPermissions.isEmpty())
    }

    @Test
    fun `getNeededPermissions returns list of ungranted permissions`() {
        every {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH
            )
        } returns PackageManager.PERMISSION_DENIED
        every {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        } returns PackageManager.PERMISSION_DENIED
        every {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } returns PackageManager.PERMISSION_DENIED

        val neededPermissions = BlePermission.getNeededPermissions(context)

        assertTrue(neededPermissions.contains(Manifest.permission.BLUETOOTH))
        assertTrue(neededPermissions.contains(Manifest.permission.BLUETOOTH_ADMIN))
        assertTrue(neededPermissions.contains(Manifest.permission.ACCESS_FINE_LOCATION))
    }

}