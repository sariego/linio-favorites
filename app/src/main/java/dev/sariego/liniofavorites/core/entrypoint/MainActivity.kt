package dev.sariego.liniofavorites.core.entrypoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.ui.res.stringResource
import com.example.myapplication.ui.theme.FavoritesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.sariego.liniofavorites.R
import dev.sariego.liniofavorites.app.favorites.presentation.screens.FavoritesScreen


@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavoritesAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = { TopAppBar { Text(stringResource(R.string.my_favorites_title)) } }
                    ) {
                        FavoritesScreen()
                    }
                }
            }
        }
    }
}