package com.odell.mycomic.data

import android.content.Context

/* Container for Dependency Injection */
interface AppContainer {
    val comicsRepository: ComicsRepository
}

/* Implementation of app container for offline use */
class AppDataContainer(private val context: Context) : AppContainer {
    override val comicsRepository: ComicsRepository by lazy {
        OfflineComicsRepo(ComicDatabase.getDatabase(context).comicDao())
    }
}