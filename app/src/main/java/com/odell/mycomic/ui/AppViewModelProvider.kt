package com.odell.mycomic.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.odell.mycomic.ComicApplication
import com.odell.mycomic.ui.home.HomeViewModel
import com.odell.mycomic.ui.models.ComicDetailsViewModel
import com.odell.mycomic.ui.models.ComicEditViewModel
import com.odell.mycomic.ui.models.ComicEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            ComicEditViewModel(
                this.createSavedStateHandle(),
                comicApplication().container.comicsRepository
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            ComicEntryViewModel(comicApplication().container.comicsRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ComicDetailsViewModel(
                this.createSavedStateHandle(),
                comicApplication().container.comicsRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(comicApplication().container.comicsRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.comicApplication(): ComicApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ComicApplication)
