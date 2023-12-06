package com.proco.app


import android.app.Application
import cat.ereza.customactivityoncrash.config.CaocConfig
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale


@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Locale.setDefault(Locale.US)
        crashHandler()
    }


    private fun crashHandler() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
            .enabled(true) //default: true
            .showErrorDetails(true) //default: true
            .showRestartButton(true) //default: true
            .logErrorOnRestart(true) //default: true
            .trackActivities(true) //default: false
            .minTimeBetweenCrashesMs(2000) //default: 3000
//            .errorDrawable(com.proco.ui.R.drawable.ic_crash) //default: bug image
            //            .restartActivity(CrashActivity.class) //default: null (your app's launch activity)
            //            .errorActivity(CrashActivity.class) //default: null (default error activity)
            .apply()
    }

}