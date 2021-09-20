package dev.sariego.liniofavorites.app.favorites.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.sariego.liniofavorites.R
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesScreenState.*
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesViewModel

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val state = favoritesViewModel.states.collectAsState()
    when (state.value) {
        Loading -> CircularProgressIndicator()
        is DisplayingError -> {
            val message = (state.value as DisplayingError).throwable.message
            Text(message.orEmpty())
        }
        is DisplayingFavorites -> {
            val data = state.value as DisplayingFavorites
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
            ) {
                items(data.collections) {

                }
                item {
                    val header = "${stringResource(R.string.my_favorites_header)} (${data.products.size})"
                    Text(header)
                }
                items(data.products) {
                    
                }
            }

        }
    }
}