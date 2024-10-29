package com.odell.mycomic

import android.app.Application
import com.odell.mycomic.data.AppContainer
import com.odell.mycomic.data.AppDataContainer

class ComicApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}