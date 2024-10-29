package com.odell.mycomic.ui.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odell.mycomic.data.ComicsRepository
import com.odell.mycomic.ui.screens.ComicEditDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ComicEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val comicsRepository: ComicsRepository
): ViewModel() {
    var comicUiState by mutableStateOf(ComicUiState())
        private set

    private val comicId: Int = checkNotNull(savedStateHandle[ComicEditDestination.COMIC_ID_ARG])

    init {
        viewModelScope.launch {
            comicUiState = comicsRepository.getComicStream(comicId)
                .filterNotNull()
                .first()
                .toComicUiState(true)
        }
    }


    suspend fun updateItem() {
        if (validateInput(comicUiState.comicDetails)) {
            comicsRepository.updateComic(comicUiState.comicDetails.toItem())
        }
    }

    fun updateUiState(itemDetails: ComicDetails) {
        comicUiState =
            ComicUiState(comicDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ComicDetails = comicUiState.comicDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && publisher.isNotBlank() && issue.isNotBlank()
        }
    }
}