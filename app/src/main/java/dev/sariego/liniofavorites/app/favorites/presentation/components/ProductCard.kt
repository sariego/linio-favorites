package dev.sariego.liniofavorites.app.favorites.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dev.sariego.liniofavorites.R
import dev.sariego.liniofavorites.app.favorites.domain.entities.Badges.*
import dev.sariego.liniofavorites.app.favorites.domain.entities.Product

@ExperimentalCoilApi
@Composable
fun ProductCard(data: Product, modifier: Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1.0F)
            .clip(RoundedCornerShape(8.dp))
            .clickable { },
    ) {
        Image(
            painter = rememberImagePainter(data.image),
            contentDescription = data.name,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            Modifier
                .sizeIn(minWidth = 30.dp)
                .align(Alignment.TopStart)
        ) {
            data.badges.forEach { badge ->
                when (badge) {
                    LINIO_PLUS -> Image(
                        painter = rememberImagePainter(R.drawable.nd_ic_plus_30),
                        contentDescription = stringResource(R.string.cd_badge_linio_plus),
                        modifier = Modifier.size(30.dp)

                    )
                    LINIO_PLUS_48 -> Image(
                        painter = rememberImagePainter(R.drawable.nd_ic_plus_48_30),
                        contentDescription = stringResource(R.string.cd_badge_linio_plus_48),
                        modifier = Modifier.size(30.dp)
                    )
                    REFURBISHED -> Image(
                        painter = rememberImagePainter(R.drawable.nd_ic_refurbished_30),
                        contentDescription = stringResource(R.string.cd_badge_refurbished),
                        modifier = Modifier.size(30.dp)
                    )
                    NEW -> Image(
                        painter = rememberImagePainter(R.drawable.nd_ic_new_30),
                        contentDescription = stringResource(R.string.cd_badge_new),
                        modifier = Modifier.size(30.dp)
                    )
                    IMPORTED -> Image(
                        painter = rememberImagePainter(R.drawable.nd_ic_international_30),
                        contentDescription = stringResource(R.string.cd_badge_imported),
                        modifier = Modifier.size(30.dp)
                    )
                    FREE_SHIPPING -> Image(
                        painter = rememberImagePainter(R.drawable.nd_ic_free_shipping_30),
                        contentDescription = stringResource(R.string.cd_badge_free_shipping),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
        Image(
            painter = rememberImagePainter(R.drawable.nd_ic_24_fav_on_pink_white_circle),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .alpha(0.8F)
                .padding(4.dp)
                .align(Alignment.TopEnd)
        )
    }
}