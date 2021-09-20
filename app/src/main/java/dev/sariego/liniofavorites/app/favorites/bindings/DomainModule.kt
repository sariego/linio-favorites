package dev.sariego.liniofavorites.app.favorites.bindings

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dev.sariego.liniofavorites.app.favorites.data.repositories.ThrottlingFavoritesRepository
import dev.sariego.liniofavorites.app.favorites.domain.repositories.FavoritesRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
interface DomainModule {

    @Binds
    fun bindRepository(impl: ThrottlingFavoritesRepository): FavoritesRepository
}
