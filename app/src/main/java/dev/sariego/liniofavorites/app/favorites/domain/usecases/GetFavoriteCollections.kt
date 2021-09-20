package dev.sariego.liniofavorites.app.favorites.domain.usecases

import dev.sariego.liniofavorites.app.favorites.domain.repositories.FavoritesRepository
import javax.inject.Inject

class GetFavoriteCollections @Inject constructor(
    private val repo: FavoritesRepository,
) {
    suspend operator fun invoke() = repo.getFavoriteCollections()
}
