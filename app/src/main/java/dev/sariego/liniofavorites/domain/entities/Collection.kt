package dev.sariego.liniofavorites.domain.entities

data class Collection(
    val id: Long,
    val name: String,
    val itemCount: Int,
    val snapshots: List<String>,
)
