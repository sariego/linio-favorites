package dev.sariego.liniofavorites.app.favorites.presentation.screens

import android.content.res.Configuration
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.sariego.liniofavorites.R
import dev.sariego.liniofavorites.app.favorites.presentation.components.CollectionCard
import dev.sariego.liniofavorites.app.favorites.presentation.components.ProductCard
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesScreenState.*
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesViewModel
import dev.sariego.liniofavorites.core.ui.theme.Header

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val state = favoritesViewModel.state.collectAsState()
    when (state.value) {
        Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        is DisplayingError -> {
            val message = (state.value as DisplayingError).cause.message
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
    val columns = NumOfColumnsInFavoritesScreen()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(data.collections.chunked(columns)) { collections ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(horizontal = 10.dp, vertical = 12.dp),
            ) {
                collections.forEach { CollectionCard(it, Modifier.weight(weight = 1F)) }
                FillEmptyColumns(collections, columns)
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
                Text(header, style = Header)
            }
        }
        items(data.products.chunked(columns)) { products ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                products.forEach { ProductCard(it, Modifier.weight(weight = 1F)) }
                FillEmptyColumns(products, columns)
            }
        }
    }
}

@Composable
private fun RowScope.FillEmptyColumns(items: List<Any>, columns: Int) {
    val diff = columns - items.size
    if (diff > 0) Spacer(modifier = Modifier.weight(diff.toFloat()))
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
fun NumOfColumnsInFavoritesScreen(): Int {
    val configuration = LocalConfiguration.current
    return when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> COLUMNS_IN_LANDSCAPE
        Configuration.ORIENTATION_PORTRAIT -> COLUMNS_IN_PORTRAIT
        else -> COLUMNS_IN_PORTRAIT
    }
}

private const val COLUMNS_IN_PORTRAIT = 2
private const val COLUMNS_IN_LANDSCAPE = 3
