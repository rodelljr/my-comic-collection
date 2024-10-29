package com.odell.mycomic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Comic::class], version = 1, exportSchema = false)
abstract class ComicDatabase : RoomDatabase() {

    abstract fun comicDao() : ComicDao

    companion object {
        @Volatile
        private var Instance: ComicDatabase? = null

        fun getDatabase(context: Context): ComicDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ComicDatabase::class.java, "comic_database")
                    .build()
                    .also { Instance = it}
            }
        }
    }
}