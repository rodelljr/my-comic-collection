package com.odell.mycomic.ui.screens

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.odell.mycomic.ComicTopAppBar
import com.odell.mycomic.R
import com.odell.mycomic.ui.AppViewModelProvider
import com.odell.mycomic.ui.models.ComicEditViewModel
import com.odell.mycomic.ui.navigation.MyNavDestination
import com.odell.mycomic.ui.theme.MyComicCollectionTheme
import kotlinx.coroutines.launch

object ComicEditDestination : MyNavDestination {
    override val route = "comic_edit"
    override val displayTitle = R.string.edit_comic_title
    const val COMIC_ID_ARG = "itemId"
    val routeWithArgs = "$route/{$COMIC_ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ComicEditViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ComicTopAppBar(
                title = stringResource(ComicEditDestination.displayTitle),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ComicEntryBody(
            comicUiState = viewModel.comicUiState,
            onComicValueChange = viewModel::updateUiState,
            onSaveClick = { coroutineScope.launch {
                viewModel.updateItem()
                navigateBack()
            }},
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComicEditScreenPreview() {
    MyComicCollectionTheme {
        ComicEditScreen(navigateBack = {}, onNavigateUp = {  })
    }

}