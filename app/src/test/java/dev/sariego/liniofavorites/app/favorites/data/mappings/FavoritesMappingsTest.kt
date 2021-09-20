package dev.sariego.liniofavorites.app.favorites.data.mappings

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.liniofavorites.app.favorites.data.models.CollectionModel
import dev.sariego.liniofavorites.app.favorites.data.models.ProductModel
import dev.sariego.liniofavorites.app.favorites.domain.entities.Badges.*
import dev.sariego.liniofavorites.test.factory.FavoritesFactory
import org.junit.Test

@SmallTest
class FavoritesMappingsTest {

    private val mappings = FavoritesMappings()

    @Test
    fun `asCollections map direct values correctly`() {
        val apiData = FavoritesFactory.makeFavoritesApiData()
        val collections = with(mappings) { apiData.asCollections() }

        (collections zip apiData).forEach { (actual, expected) ->
            assertThat(actual.id).isEqualTo(expected.id)
            assertThat(actual.name).isEqualTo(expected.name)
        }
    }

    @Test
    fun `asCollections map products values correctly`() {
        val apiData = FavoritesFactory.makeFavoritesApiData()
        val collections = with(mappings) { apiData.asCollections() }

        (collections zip apiData).forEach { (actual, expected) ->
            assertThat(actual.itemCount).isEqualTo(expected.products.size)
            assertThat(actual.snapshots).isEqualTo(expected.products.map(ProductModel::image))
        }
    }

    @Test
    fun `asProducts map direct values correctly`() {
        val apiData = FavoritesFactory.makeFavoritesApiData()
        val apiProducts = apiData.flatMap(CollectionModel::products)
        val products = with(mappings) { apiData.asProducts() }

        (products zip apiProducts).forEach { (actual, expected) ->
            assertThat(actual.id).isEqualTo(expected.id)
            assertThat(actual.name).isEqualTo(expected.name)
            assertThat(actual.image).isEqualTo(expected.image)
            assertThat(actual.url).isEqualTo(expected.url)
        }
    }

    @Test
    fun `asProduct map badges correctly`() {
        val plusLevelOneModel = FavoritesFactory.makeProductModel().copy(
            linioPlusLevel = ApiValues.LINIO_PLUS_LEVEL_1
        )
        val plusLevelTwoModel = FavoritesFactory.makeProductModel().copy(
            linioPlusLevel = ApiValues.LINIO_PLUS_LEVEL_2
        )
        val refurbishedModel = FavoritesFactory.makeProductModel().copy(
            conditionType = ApiValues.CONDITION_TYPE_REFURBISHED
        )
        val newModel = FavoritesFactory.makeProductModel().copy(
            conditionType = ApiValues.CONDITION_TYPE_NEW
        )
        val importedModel = FavoritesFactory.makeProductModel().copy(
            imported = true
        )
        val freeShippingModel = FavoritesFactory.makeProductModel().copy(
            freeShipping = true
        )

        val plusLevelOneEntity = with(mappings) { plusLevelOneModel.asProduct() }
        assertThat(plusLevelOneEntity.badges).contains(LINIO_PLUS)
        assertThat(plusLevelOneEntity.badges).doesNotContain(LINIO_PLUS_48)

        val plusLevelTwoEntity = with(mappings) { plusLevelTwoModel.asProduct() }
        assertThat(plusLevelTwoEntity.badges).contains(LINIO_PLUS_48)
        assertThat(plusLevelTwoEntity.badges).doesNotContain(LINIO_PLUS)

        val refurbishedEntity = with(mappings) { refurbishedModel.asProduct() }
        assertThat(refurbishedEntity.badges).contains(REFURBISHED)
        assertThat(refurbishedEntity.badges).doesNotContain(NEW)

        val newEntity = with(mappings) { newModel.asProduct() }
        assertThat(newEntity.badges).contains(NEW)
        assertThat(newEntity.badges).doesNotContain(REFURBISHED)

        val importedEntity = with(mappings) { importedModel.asProduct() }
        assertThat(importedEntity.badges).contains(IMPORTED)

        val freeShippingEntity = with(mappings) { freeShippingModel.asProduct() }
        assertThat(freeShippingEntity.badges).contains(FREE_SHIPPING)
    }
}
