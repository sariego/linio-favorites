package dev.sariego.liniofavorites.core.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sariego.liniofavorites.core.dispatcher.DefaultDispatcherProvider
import dev.sariego.liniofavorites.core.dispatcher.DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}