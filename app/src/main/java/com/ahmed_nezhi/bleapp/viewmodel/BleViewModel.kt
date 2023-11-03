package com.ahmed_nezhi.bleapp.viewmodel

import androidx.lifecycle.ViewModel
import com.ahmed_nezhi.bleapp.BleManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Create by A.Nezhi on 03/11/2023.
 */
@HiltViewModel
class BleViewModel @Inject constructor(private val bleManager: BleManager) : ViewModel() {

    val screenColor: StateFlow<Int> = bleManager.screenColor

}