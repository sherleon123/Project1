package com.example.project1
import android.app.Application
import utilities.SignalManager
import utilities.ImageLoader
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
        ImageLoader.init(this)
    }
}