package com.odell.mycomic.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odell.mycomic.data.ComicsRepository
import com.odell.mycomic.data.Comic
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/* ViewModel to get all items from the Room database*/
class HomeViewModel(comicsRepository: ComicsRepository):  ViewModel(){
    val homeUiState: StateFlow<HomeUiState> =
        comicsRepository.getAllComicsStream().map {HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/* UI state for HomeMain*/
data class HomeUiState(val comicList: List<Comic> = listOf())