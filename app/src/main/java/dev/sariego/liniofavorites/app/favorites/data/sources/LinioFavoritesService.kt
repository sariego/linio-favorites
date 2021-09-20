package dev.sariego.liniofavorites.app.favorites.data.sources

import dev.sariego.liniofavorites.app.favorites.data.models.CollectionModel
import retrofit2.http.GET

interface LinioFavoritesService {

    @GET("api/favorites")
    suspend fun getFavoriteCollections(): List<CollectionModel>
}