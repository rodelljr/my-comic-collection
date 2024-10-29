package com.odell.mycomic.ui.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.odell.mycomic.data.Comic
import com.odell.mycomic.data.ComicsRepository

class ComicEntryViewModel(private val comicsRepository: ComicsRepository
): ViewModel() {

    /* Holds current comic ui state  */
    var itemUiState by mutableStateOf(ComicUiState())
        private set

    /* Updates the [comicUiState] with the value provided in the argument.  */
    fun updateUiState(itemDetails: ComicDetails) {
        itemUiState =
            ComicUiState(comicDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ComicDetails = itemUiState.comicDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && publisher.isNotBlank() && issue.isNotBlank()
        }
    }
    suspend fun saveItem() {
        if (validateInput()) {
            comicsRepository.insertComic(itemUiState.comicDetails.toItem())
        }
    }
}

/* Represents Ui State for a Comic. */
data class ComicUiState(
    val comicDetails: ComicDetails = ComicDetails(),
    val isEntryValid: Boolean = false
)

data class ComicDetails(
    val id: Int = 0,
    val name: String = "",
    val publisher: String = "",
    val issue: String = "",
)

/**
 * Extension function to convert [ComicDetails] to [Comic]. If the value of [ComicDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ComicmDetails.issue] is not a valid [Int], then the issue will be set to 0
 */
fun ComicDetails.toItem(): Comic = Comic(
    id = id,
    name = name,
    publisher = publisher,
    issue = issue.toIntOrNull() ?: 0
)


/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun Comic.toComicUiState(isEntryValid: Boolean = false): ComicUiState = ComicUiState(
    comicDetails = this.toComicDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun Comic.toComicDetails(): ComicDetails = ComicDetails(
    id = id,
    name = name,
    publisher = publisher,
    issue = issue.toString()
)