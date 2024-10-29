package com.odell.mycomic.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comic: Comic)

    @Update
    suspend fun update(comic: Comic)

    @Delete
    suspend fun delete(comic: Comic)

    @Query("SELECT * from comics WHERE id = :id")
    fun getItem(id: Int): Flow<Comic>

    @Query("SELECT * from comics ORDER BY name ASC")
    fun getAllItems(): Flow<List<Comic>>
}