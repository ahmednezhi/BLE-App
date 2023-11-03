package com.ahmed_nezhi.bleapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ahmed_nezhi.bleapp.ui.BleScreen
import com.ahmed_nezhi.bleapp.ui.theme.BLEAppTheme
import com.ahmed_nezhi.bleapp.utils.BlePermission.getNeededPermissions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bleManager: BleManager

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) {
            // All permissions are granted, proceed with BLE operations
            initializeBleOperations()
        } else {
            Toast.makeText(
                applicationContext,
                "You need to grant all permissions in order to use BLE features",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BLEAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BleScreen()
                }
            }
        }
        // Check for permissions and request them if not granted
        checkAndRequestPermissions()
    }

    private fun initializeBleOperations() {
        bleManager.initialize()
    }

    private fun checkAndRequestPermissions() {
        val requiredPermissions = getNeededPermissions(this)
        if (requiredPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(requiredPermissions.toTypedArray())
        } else {
            initializeBleOperations()
        }
    }
}
