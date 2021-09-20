package dev.sariego.liniofavorites.app.favorites.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionModel(
    val id: Long,
    val name: String,
    val products: Map<String, ProductModel>
)
