package dev.sariego.liniofavorites.app.favorites.domain.entities

data class Product(
    val id: Long,
    val name: String,
    val image: String,
    val url: String,
    val badges: List<Badges>,
)
