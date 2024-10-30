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
package com.odell.mycomic

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.odell.mycomic.data.Comic
import com.odell.mycomic.data.ComicDao
import com.odell.mycomic.data.ComicDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ComicDaoTest {
    private lateinit var comicDao: ComicDao
    private lateinit var inventoryDatabase: ComicDatabase
    private var comic1 = Comic(100, "Batman", 100, "DC Comics")
    private var comic2 = Comic(101, "Green Lantern", 100,  "DC Comics")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, ComicDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        comicDao = inventoryDatabase.comicDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = comicDao.getAllItems().first()
        assertEquals(allItems[0], comic1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = comicDao.getAllItems().first()
        assertEquals(allItems[0], comic1)
        assertEquals(allItems[1], comic2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemsToDb()
        comicDao.update(Comic(100, "Batman", 100, "DC Comics"))
        comicDao.update(Comic(101, "Green Lantern", 100,  "DC Comics"))

        val allItems = comicDao.getAllItems().first()
        assertEquals(allItems[0], Comic(100, "Batman", 100, "DC Comics"))
        assertEquals(allItems[1], Comic(101, "Green Lantern", 100,  "DC Comics"))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB()  = runBlocking{
        addTwoItemsToDb()
        comicDao.delete(comic1)
        comicDao.delete(comic2)
        val allItems = comicDao.getAllItems().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = comicDao.getItem(100)
        assertEquals(item.first(), comic1)
    }

    private suspend fun addOneItemToDb() {
        comicDao.insert(comic1)
    }

    private suspend fun addTwoItemsToDb() {
        comicDao.insert(comic1)
        comicDao.insert(comic2)
    }


}