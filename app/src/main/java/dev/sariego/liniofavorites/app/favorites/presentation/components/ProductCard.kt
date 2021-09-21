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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import dev.sariego.liniofavorites.R
import dev.sariego.liniofavorites.app.favorites.domain.entities.Badges.*
import dev.sariego.liniofavorites.app.favorites.domain.entities.Product

@ExperimentalCoilApi
@Composable
fun ProductCard(data: Product, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1.0F)
            .clip(RoundedCornerShape(4.dp))
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
                    LINIO_PLUS -> ProductIcon(
                        resource = R.drawable.nd_ic_plus_30,
                        contentDescription = stringResource(R.string.cd_badge_linio_plus)
                    )
                    LINIO_PLUS_48 -> ProductIcon(
                        resource = R.drawable.nd_ic_plus_48_30,
                        contentDescription = stringResource(R.string.cd_badge_linio_plus_48)
                    )
                    REFURBISHED -> ProductIcon(
                        resource = R.drawable.nd_ic_refurbished_30,
                        contentDescription = stringResource(R.string.cd_badge_refurbished)
                    )
                    NEW -> ProductIcon(
                        resource = R.drawable.nd_ic_new_30,
                        contentDescription = stringResource(R.string.cd_badge_new)
                    )
                    IMPORTED -> ProductIcon(
                        resource = R.drawable.nd_ic_international_30,
                        contentDescription = stringResource(R.string.cd_badge_imported)
                    )
                    FREE_SHIPPING -> ProductIcon(
                        resource = R.drawable.nd_ic_free_shipping_30,
                        contentDescription = stringResource(R.string.cd_badge_free_shipping)
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

@ExperimentalCoilApi
@Composable
private fun ProductIcon(resource: Int, contentDescription: String) {
    Image(
        painter = rememberImagePainter(resource),
        contentDescription = contentDescription,
        modifier = Modifier.size(30.dp)

    )
}
