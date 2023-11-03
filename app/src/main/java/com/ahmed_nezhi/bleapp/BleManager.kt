package com.ahmed_nezhi.bleapp

/**
 * Create by A.Nezhi on 03/11/2023.
 */


import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.os.ParcelUuid
import com.ahmed_nezhi.bleapp.utils.BlePermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class BleManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val bluetoothManager: BluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter
    private var bluetoothGattServer: BluetoothGattServer? = null

    private val _screenColor = MutableStateFlow<Int>(999) // Default color green
    val screenColor: StateFlow<Int> = _screenColor.asStateFlow()


    fun initialize() {
        // Start BLE advertising, set up GATT server, etc.
        startAdvertising()
        setupGattServer()
        // Any other initialization code for your BLE functionality
    }

    private fun startAdvertising() {
        if (!BlePermission.hasBlePermissions(
                context
            )
        ) {
            return
        }
        val advertiser = bluetoothAdapter.bluetoothLeAdvertiser
        val settings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
            .setConnectable(true)
            .build()

        val data = AdvertiseData.Builder()
            .setIncludeDeviceName(true)
            .addServiceUuid(ParcelUuid.fromString("13b402b6-ff2c-4175-a157-7e888b192a45"))
            .build()

        bluetoothAdapter.name = "Test AN"



        advertiser?.startAdvertising(settings, data, advertiseCallback)


    }

    fun stopAdvertising() {
        val advertiser = bluetoothAdapter.bluetoothLeAdvertiser
        advertiser?.stopAdvertising(advertiseCallback)

    }

    private fun setupGattServer() {

        bluetoothGattServer = bluetoothManager.openGattServer(context, gattServerCallback)
        val service = BluetoothGattService(
            UUID.fromString("46c7ec1a-a9ee-4c62-a207-afb1add801ad"),
            BluetoothGattService.SERVICE_TYPE_PRIMARY
        )

        val characteristic = BluetoothGattCharacteristic(
            UUID.fromString("c0a130aa-7409-484a-935a-ff81c1d4ef96"),
            BluetoothGattCharacteristic.PROPERTY_WRITE,
            BluetoothGattCharacteristic.PERMISSION_WRITE
        )
        service.addCharacteristic(characteristic)

        bluetoothGattServer?.addService(service)


    }

    fun handleCharacteristicWriteRequest(
        device: BluetoothDevice,
        requestId: Int,
        characteristic: BluetoothGattCharacteristic,
        preparedWrite: Boolean,
        responseNeeded: Boolean,
        offset: Int,
        value: ByteArray
    ) {
        // The logic from onCharacteristicWriteRequest goes here
        if (characteristic.uuid == UUID.fromString("c0a130aa-7409-484a-935a-ff81c1d4ef96")) {
            if (value.isNotEmpty()) {
                val colorValue = value[0].toInt()
                _screenColor.value = colorValue
                if (responseNeeded) {
                    bluetoothGattServer?.sendResponse(
                        device,
                        requestId,
                        BluetoothGatt.GATT_SUCCESS,
                        offset,
                        value
                    )
                }
            }
        }
    }

    private val gattServerCallback = object : BluetoothGattServerCallback() {
        override fun onConnectionStateChange(device: BluetoothDevice, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)
            // Handle connection state change
        }

        override fun onCharacteristicWriteRequest(
            device: BluetoothDevice,
            requestId: Int,
            characteristic: BluetoothGattCharacteristic,
            preparedWrite: Boolean,
            responseNeeded: Boolean,
            offset: Int,
            value: ByteArray
        ) {
            super.onCharacteristicWriteRequest(
                device,
                requestId,
                characteristic,
                preparedWrite,
                responseNeeded,
                offset,
                value
            )
            handleCharacteristicWriteRequest(
                device,
                requestId,
                characteristic,
                preparedWrite,
                responseNeeded,
                offset,
                value
            )
        }
    }

    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            super.onStartSuccess(settingsInEffect)
            setupGattServer()
        }

        override fun onStartFailure(errorCode: Int) {
            super.onStartFailure(errorCode)
            // Handle advertising start failure
        }
    }


}
