package dev.sariego.liniofavorites.app.favorites.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sariego.liniofavorites.app.favorites.domain.usecases.GetFavoriteCollections
import dev.sariego.liniofavorites.app.favorites.domain.usecases.GetFavoriteProducts
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesScreenState.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteCollections: GetFavoriteCollections,
    private val getFavoriteProducts: GetFavoriteProducts,
) : ViewModel() {

    val states: StateFlow<FavoritesScreenState> = flow {
        emit(getFavoriteCollections() to getFavoriteProducts())
    }
        .map { (collections, products) ->
            DisplayingFavorites(collections, products) as FavoritesScreenState
        }
        .catch { cause -> emit(DisplayingError(cause)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Loading
        )
}