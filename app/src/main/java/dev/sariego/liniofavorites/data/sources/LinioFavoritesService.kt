package dev.sariego.liniofavorites.data.sources

import dev.sariego.liniofavorites.data.models.CollectionModel
import retrofit2.http.GET

interface LinioFavoritesService {

    @GET("api/favorites")
    suspend fun getFavoriteCollections(): List<CollectionModel>
}