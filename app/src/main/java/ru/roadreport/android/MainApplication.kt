package ru.roadreport.android

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication @Inject constructor() : Application() {
    override fun onCreate() {
        super.onCreate()

        res = resources
    }

    companion object{
        lateinit var res: Resources
    }
}