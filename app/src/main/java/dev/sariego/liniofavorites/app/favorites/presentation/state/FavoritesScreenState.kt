package dev.sariego.liniofavorites.app.favorites.presentation.state

import dev.sariego.liniofavorites.app.favorites.domain.entities.Collection
import dev.sariego.liniofavorites.app.favorites.domain.entities.Product

sealed class FavoritesScreenState {

    object Loading : FavoritesScreenState()

    data class DisplayingError(val throwable: Throwable) : FavoritesScreenState()

    data class DisplayingFavorites(
        val collections: List<Collection>,
        val products: List<Product>,
    ): FavoritesScreenState()
}
