package dev.sariego.liniofavorites.domain.usecases

import dev.sariego.liniofavorites.domain.repositories.FavoritesRepository
import javax.inject.Inject

class GetFavoriteCollections @Inject constructor(
    private val repo: FavoritesRepository,
) {
    suspend operator fun invoke() = repo.getFavoriteCollections()
}
