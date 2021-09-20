package dev.sariego.liniofavorites.app.favorites.domain.repositories

import dev.sariego.liniofavorites.app.favorites.domain.entities.Collection
import dev.sariego.liniofavorites.app.favorites.domain.entities.Product

interface FavoritesRepository {

    suspend fun getFavoriteCollections(): List<Collection>

    suspend fun getFavoriteProducts(): List<Product>
}
