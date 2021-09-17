package dev.sariego.liniofavorites.data.repositories

import dev.sariego.liniofavorites.data.mappings.FavoritesMappings
import dev.sariego.liniofavorites.data.models.CollectionModel
import dev.sariego.liniofavorites.data.sources.LinioFavoritesService
import dev.sariego.liniofavorites.domain.entities.Collection
import dev.sariego.liniofavorites.domain.entities.Product
import dev.sariego.liniofavorites.domain.repositories.FavoritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import javax.inject.Inject

class SimpleFavoritesRepository @Inject constructor(
    private val service: LinioFavoritesService,
    private val mappings: FavoritesMappings,
) : FavoritesRepository {

    private val serviceFlow = flow<List<CollectionModel>> {
        service.getFavoriteCollections()
    }.shareIn(
        scope = CoroutineScope(Dispatchers.IO) + SupervisorJob(),
        started = SharingStarted.WhileSubscribed(replayExpirationMillis = 2_000),
        replay = 1
    )

    override suspend fun getFavoriteCollections(): List<Collection> = with(mappings) {
        serviceFlow.single().asCollections()
    }

    override suspend fun getFavoriteProducts(): List<Product> = with(mappings) {
        serviceFlow.single().asProducts()
    }
}