package dev.sariego.liniofavorites.core.entrypoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint
import dev.sariego.liniofavorites.R
import dev.sariego.liniofavorites.app.favorites.presentation.screens.FavoritesScreen
import dev.sariego.liniofavorites.core.ui.theme.AppTitle
import dev.sariego.liniofavorites.core.ui.theme.FavoritesAppTheme
import dev.sariego.liniofavorites.core.ui.theme.FavoritesBackground

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavoritesAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Box(Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.my_favorites_title),
                                        style = AppTitle,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            },
                            actions = {
                                      Icon(
                                          painter = rememberImagePainter(R.drawable.nd_ic_add_collection_32),
                                          contentDescription = null,
                                          modifier = Modifier.clickable {  }
                                      ) // this action does nothing
                            },
                            backgroundColor = FavoritesBackground
                        )
                    },
                    backgroundColor = FavoritesBackground
                ) {
                    FavoritesScreen()
                }
            }
        }
    }
}