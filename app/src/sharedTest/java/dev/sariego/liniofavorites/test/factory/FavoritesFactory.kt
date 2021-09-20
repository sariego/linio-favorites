package dev.sariego.liniofavorites.test.factory

import dev.sariego.liniofavorites.app.favorites.data.mappings.ApiValues
import dev.sariego.liniofavorites.app.favorites.data.models.CollectionModel
import dev.sariego.liniofavorites.app.favorites.data.models.ProductModel
import dev.sariego.liniofavorites.app.favorites.domain.entities.Collection
import dev.sariego.liniofavorites.app.favorites.domain.entities.Product
import io.github.serpro69.kfaker.Faker

object FavoritesFactory {

    private val faker = Faker()

    fun makeCollection(): Collection {
        val count = faker.random.nextInt(100)
        return Collection(
            id = faker.random.nextLong(),
            name = faker.witcher.schools(),
            itemCount = count,
            snapshots = List(count) { "https://fakeimg.pl/100/" }
        )
    }

    fun makeProduct(
        id: Long = faker.random.nextLong()
    ) = Product(
        id = id,
        name = faker.commerce.productName(),
        image = "https://fakeimg.pl/300/",
        url = "/mapi/v1/$id",
        badges = emptyList()
    )

    fun makeFavoritesApiData(count: Int = 5) = List(count) {
        val productsInCollection = faker.random.nextInt(100)
        CollectionModel(
            id = faker.random.nextLong(),
            name = faker.harryPotter.houses(),
            products = mapOf(
                // spread love <3
                *Array(productsInCollection) {
                    faker.random.nextUUID() to makeProductModel()
                }
            )
        )
    }

    fun makeProductModel(
        id: Long = faker.random.nextLong()
    ) = ProductModel(
        id = id,
        name = faker.commerce.productName(),
        image = "https://fakeimg.pl/300/",
        url = "/mapi/v1/$id",
        linioPlusLevel = faker.random.randomValue(
            listOf(ApiValues.LINIO_PLUS_LEVEL_1, ApiValues.LINIO_PLUS_LEVEL_2)
        ),
        conditionType = faker.random.randomValue(
            listOf(ApiValues.CONDITION_TYPE_REFURBISHED, ApiValues.CONDITION_TYPE_NEW)
        ),
        freeShipping = faker.random.nextBoolean(),
        imported = faker.random.nextBoolean(),
    )
}