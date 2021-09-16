package dev.sariego.liniofavorites.domain.repositories

import dev.sariego.liniofavorites.domain.entities.Collection
import dev.sariego.liniofavorites.domain.entities.Product

interface FavoritesRepository {

    suspend fun getFavoriteCollections(): List<Collection>

    suspend fun getFavoriteProducts(): List<Product>
}
