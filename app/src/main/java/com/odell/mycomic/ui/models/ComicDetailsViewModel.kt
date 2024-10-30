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
package com.odell.mycomic.ui.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odell.mycomic.data.ComicsRepository
import com.odell.mycomic.ui.screens.ComicDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ComicDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val comicsRepository: ComicsRepository
): ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ComicDetailsDestination.COMIC_ID_ARG])

    val uiState: StateFlow<ComicDetailsUiState> =
        comicsRepository.getComicStream(itemId)
            .filterNotNull()
            .map {
                ComicDetailsUiState(outOfStock = it.issue <= 0, comicDetails = it.toComicDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ComicDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteItem() {
        comicsRepository.deleteComic(uiState.value.comicDetails.toItem())
    }
}

/* UI state for ComicDetailsScreen */
data class ComicDetailsUiState(
    val outOfStock: Boolean = true,
    val comicDetails: ComicDetails = ComicDetails()
)