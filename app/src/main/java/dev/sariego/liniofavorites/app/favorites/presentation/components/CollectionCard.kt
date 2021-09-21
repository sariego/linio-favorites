package dev.sariego.liniofavorites.app.favorites.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dev.sariego.liniofavorites.app.favorites.domain.entities.Collection
import dev.sariego.liniofavorites.core.ui.theme.Body
import dev.sariego.liniofavorites.core.ui.theme.Caption
import dev.sariego.liniofavorites.core.ui.theme.FavoritesBackground

@ExperimentalCoilApi
@Composable
fun CollectionCard(data: Collection, modifier: Modifier = Modifier) {
    Column(modifier = modifier.clickable { }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = FavoritesBackground)
                .height(IntrinsicSize.Min)
                .padding(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(data.snapshots.getOrNull(0)),
                contentDescription = null, // no description needed, card has title info
                modifier = Modifier
                    .weight(2F)
                    .aspectRatio(1.0F)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight(0.95F)
                    .weight(1F)
            ) {
                Image(
                    painter = rememberImagePainter(data.snapshots.getOrNull(1)),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().weight(1F)
                )
                Image(
                    painter = rememberImagePainter(data.snapshots.getOrNull(2)),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().weight(1F)
                )
            }
        }
        Text(data.name, modifier = Modifier.padding(top = 12.dp), style = Body)
        Text(data.itemCount.toString(), style = Caption)
    }
}