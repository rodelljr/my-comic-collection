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