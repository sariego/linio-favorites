package dev.sariego.liniofavorites.data.mappings

import dev.sariego.liniofavorites.data.models.CollectionModel
import dev.sariego.liniofavorites.data.models.ProductModel
import dev.sariego.liniofavorites.domain.entities.Badges.*
import dev.sariego.liniofavorites.domain.entities.Collection
import dev.sariego.liniofavorites.domain.entities.Product
import javax.inject.Inject

class FavoritesMappings @Inject constructor() {

    fun List<CollectionModel>.asCollections(): List<Collection> = map { model ->
        Collection(
            id = model.id,
            name = model.name,
            itemCount = model.products.size,
            snapshots = model.products.map(ProductModel::image),
        )
    }

    fun List<CollectionModel>.asProducts(): List<Product> = flatMap { collection ->
        collection.products.map { model -> model.asProduct() }
    }

    fun ProductModel.asProduct() = Product(
        id = id,
        name = name,
        image = image,
        url = url,
        badges = listOfNotNull(
            LINIO_PLUS.takeIf { linioPlusLevel == ApiValues.LINIO_PLUS_LEVEL_1 },
            LINIO_PLUS_48.takeIf { linioPlusLevel == ApiValues.LINIO_PLUS_LEVEL_2 },
            REFURBISHED.takeIf { conditionType == ApiValues.CONDITION_TYPE_REFURBISHED },
            NEW.takeIf { conditionType == ApiValues.CONDITION_TYPE_NEW },
            IMPORTED.takeIf { imported },
            FREE_SHIPPING.takeIf { freeShipping },
        )
    )
}
