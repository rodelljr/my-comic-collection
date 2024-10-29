package com.odell.mycomic.data

import kotlinx.coroutines.flow.Flow

class OfflineComicsRepo(private val comicDao: ComicDao) : ComicsRepository{
    override fun getAllComicsStream(): Flow<List<Comic>> = comicDao.getAllItems()

    override fun getComicStream(id: Int): Flow<Comic> = comicDao.getItem(id)

    override suspend fun insertComic(comic: Comic) = comicDao.insert(comic)

    override suspend fun deleteComic(comic: Comic) = comicDao.delete(comic)

    override suspend fun updateComic(comic: Comic) = comicDao.update(comic)

}