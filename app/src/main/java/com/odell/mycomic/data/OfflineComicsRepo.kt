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

import kotlinx.coroutines.flow.Flow

class OfflineComicsRepo(private val comicDao: ComicDao) : ComicsRepository{
    override fun getAllComicsStream(): Flow<List<Comic>> = comicDao.getAllItems()

    override fun getComicStream(id: Int): Flow<Comic> = comicDao.getItem(id)

    override suspend fun insertComic(comic: Comic) = comicDao.insert(comic)

    override suspend fun deleteComic(comic: Comic) = comicDao.delete(comic)

    override suspend fun updateComic(comic: Comic) = comicDao.update(comic)

}