package dev.sariego.liniofavorites.domain.usecases

import dev.sariego.liniofavorites.domain.repositories.FavoritesRepository
import javax.inject.Inject

class GetFavoriteProducts @Inject constructor(
    private val repo: FavoritesRepository,
) {
    suspend operator fun invoke() = repo.getFavoriteProducts()
}
