package com.example.project1
import android.app.Application
import utilities.BackgroundMusicPlayer
import utilities.SignalManager
import utilities.ImageLoader
import utilities.SharedPreferncesManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
        ImageLoader.init(this)
        BackgroundMusicPlayer.init(this)
        BackgroundMusicPlayer.getInstance().setResourceId(R.raw.background_msuic)
        SharedPreferncesManager.init(this)
    }
    override fun onTerminate() {
        super.onTerminate()
        BackgroundMusicPlayer.getInstance().stopMusic()
    }
}