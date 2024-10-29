package com.odell.mycomic.data

import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    /* Get all comics from data source */
    fun getAllComicsStream(): Flow<List<Comic>>

    /* Get a single comic from a data source by id */
    fun getComicStream(id: Int): Flow<Comic>

    /* Insert comic in to the data source */
    suspend fun insertComic(comic: Comic)

    /* Delete comic from the data source */
    suspend fun deleteComic(comic: Comic)

    /* Update comic from the data source */
    suspend fun updateComic(comic: Comic)
}