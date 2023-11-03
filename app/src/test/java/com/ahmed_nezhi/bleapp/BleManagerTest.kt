package com.ahmed_nezhi.bleapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeAdvertiser
import android.content.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import java.util.UUID

/**
 * Create by A.Nezhi on 03/11/2023.
 */
@ExperimentalCoroutinesApi
class BleManagerTest {

    private lateinit var bleManager: BleManager
    private lateinit var context: Context
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothLeAdvertiser: BluetoothLeAdvertiser

    @Before
    fun setUp() {
        // Mock the context and other dependencies
        context = mockk(relaxed = true)
        bluetoothManager = mockk(relaxed = true)
        bluetoothAdapter = mockk(relaxed = true)
        bluetoothLeAdvertiser = mockk(relaxed = true)

        // Mock the system service call to return the BluetoothManager
        every { context.getSystemService(Context.BLUETOOTH_SERVICE) } returns bluetoothManager
        every { bluetoothManager.adapter } returns bluetoothAdapter
        every { bluetoothAdapter.bluetoothLeAdvertiser } returns bluetoothLeAdvertiser

        // Create an instance of BleManager with the mocked context
        bleManager = BleManager(context)
    }

    @Test
    fun `stopAdvertising should stop BLE advertising`() {
        // Call the stopAdvertising method
        bleManager.stopAdvertising()

        // Verify that advertising stopped
        verify { bluetoothLeAdvertiser.stopAdvertising(any()) }
    }

    @Test
    fun `handleCharacteristicWriteRequest should update screen color when correct characteristic is written`() {
        // Mock a BluetoothDevice
        val mockDevice: BluetoothDevice = mockk(relaxed = true)
        val characteristicUuid = UUID.fromString("c0a130aa-7409-484a-935a-ff81c1d4ef96")
        val characteristic: BluetoothGattCharacteristic = mockk {
            every { uuid } returns characteristicUuid
        }

        // Prepare the value to be written to the characteristic
        val value = byteArrayOf(1) // This should change the screen color to red

        // Call the handleCharacteristicWriteRequest method
        bleManager.handleCharacteristicWriteRequest(
            mockDevice,
            requestId = 1,
            characteristic,
            preparedWrite = false,
            responseNeeded = true,
            offset = 0,
            value = value
        )

        // Verify that the screen color was updated
        val screenColorValue = bleManager.screenColor.value
        assertEquals(1, screenColorValue)
    }

}