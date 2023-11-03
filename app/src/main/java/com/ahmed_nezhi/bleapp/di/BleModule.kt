package com.ahmed_nezhi.bleapp.di

import android.content.Context
import com.ahmed_nezhi.bleapp.BleManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Create by A.Nezhi on 03/11/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object BleModule {

    @Singleton
    @Provides
    fun provideBleManager(@ApplicationContext context: Context): BleManager {
        return BleManager(context)
    }
}