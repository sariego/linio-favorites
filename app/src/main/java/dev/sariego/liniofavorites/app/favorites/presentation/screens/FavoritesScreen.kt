package dev.sariego.liniofavorites.app.favorites.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.sariego.liniofavorites.R
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesScreenState.*
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesViewModel

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val state = favoritesViewModel.states.collectAsState()
    when (state.value) {
        Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        is DisplayingError -> {
            val message = (state.value as DisplayingError).throwable.message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(message.orEmpty())
            }
        }
        is DisplayingFavorites -> {
            val data = state.value as DisplayingFavorites
            FavoritesInnerScreen(data)
        }
    }
}

@Composable
private fun FavoritesInnerScreen(data: DisplayingFavorites) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(data.collections.chunked(NUM_ROWS)) { collections ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                collections.forEach { Text(it.name, Modifier.weight(weight = 1F)) }
            }
        }
        item {
            val header = "${stringResource(R.string.my_favorites_header)} (${data.products.size})"
            Box(
                modifier = Modifier
                .height(77.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(header)
            }

        }
        items(data.products.chunked(NUM_ROWS)) { products ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
               products.forEach { Text(it.name, Modifier.weight(weight = 1F)) }
            }
        }
    }
}

private const val NUM_ROWS = 2
