package com.hxfood.newsdemo

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HxFoodApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: HxFoodApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        applicationContext()
    }
}