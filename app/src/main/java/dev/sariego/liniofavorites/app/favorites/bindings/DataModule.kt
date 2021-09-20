package dev.sariego.liniofavorites.app.favorites.bindings

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dev.sariego.liniofavorites.app.favorites.data.sources.LinioFavoritesService
import dev.sariego.liniofavorites.core.providers.qualifier.ServerUrl
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class DataModule {

    @Provides
    @ServerUrl
    fun provideServerUrl() = "https://www.example.api/"

    @Provides
    fun provideNetworkService(
        retrofit: Retrofit,
    ): LinioFavoritesService = retrofit.create(LinioFavoritesService::class.java)
}
