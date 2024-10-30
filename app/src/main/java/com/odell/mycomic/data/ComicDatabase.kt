/*
Copyright [2024] [Roger O'Dell]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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