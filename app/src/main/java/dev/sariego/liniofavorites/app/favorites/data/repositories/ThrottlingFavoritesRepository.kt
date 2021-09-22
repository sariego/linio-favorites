package dev.sariego.liniofavorites.app.favorites.data.repositories

import dev.sariego.liniofavorites.app.favorites.data.mappings.FavoritesMappings
import dev.sariego.liniofavorites.app.favorites.data.sources.LinioFavoritesService
import dev.sariego.liniofavorites.app.favorites.domain.entities.Collection
import dev.sariego.liniofavorites.app.favorites.domain.entities.Product
import dev.sariego.liniofavorites.app.favorites.domain.repositories.FavoritesRepository
import dev.sariego.liniofavorites.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.plus
import javax.inject.Inject

class ThrottlingFavoritesRepository @Inject constructor(
    private val service: LinioFavoritesService,
    private val mappings: FavoritesMappings,
    dispatchers: DispatcherProvider,
) : FavoritesRepository {

    private val serviceFlow = flow {
        emit(service.getFavoriteCollections())
    }.shareIn(
        scope = CoroutineScope(dispatchers.io()) + SupervisorJob(),
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = API_SERVICE_THROTTLE,
            replayExpirationMillis = 0
        ),
        replay = 1
    )

    override suspend fun getFavoriteCollections(): List<Collection> {
        val models = serviceFlow.first()
        return with(mappings) { models.asCollections() }
    }

    override suspend fun getFavoriteProducts(): List<Product> {
        val models = serviceFlow.first()
        return with(mappings) { models.asProducts() }
    }

    private companion object {
        const val API_SERVICE_THROTTLE = 2_000L // 2 seconds
    }
}
