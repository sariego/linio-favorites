package dev.sariego.liniofavorites.app.favorites.domain.entities

data class Collection(
    val id: Long,
    val name: String,
    val itemCount: Int,
    val snapshots: List<String>,
)
