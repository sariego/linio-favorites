package dev.sariego.liniofavorites.app.favorites.domain.usecases

import dev.sariego.liniofavorites.app.favorites.domain.entities.Product
import dev.sariego.liniofavorites.app.favorites.domain.repositories.FavoritesRepository
import javax.inject.Inject

class GetFavoriteProducts @Inject constructor(
    private val repo: FavoritesRepository,
) {
    suspend operator fun invoke(): List<Product> = repo.getFavoriteProducts()
}
