package dev.sariego.liniofavorites.core.providers

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sariego.liniofavorites.core.dispatcher.DefaultDispatcherProvider
import dev.sariego.liniofavorites.core.dispatcher.DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindDispatcherProvider(impl: DefaultDispatcherProvider): DispatcherProvider
}