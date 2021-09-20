package dev.sariego.liniofavorites.app.favorites.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductModel(
    val id: Long,
    val name: String,
    val image: String,
    val url: String,
    val linioPlusLevel: Int,
    val conditionType: String,
    val freeShipping: Boolean,
    val imported: Boolean,
)
