package dev.sariego.liniofavorites.data.repositories

import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.liniofavorites.data.mappings.FavoritesMappings
import dev.sariego.liniofavorites.data.sources.LinioFavoritesService
import dev.sariego.liniofavorites.test.factory.FavoritesFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
@MediumTest
class SimpleFavoritesRepositoryTest {

    private val service = mockk<LinioFavoritesService>(relaxed = true)
    private val mappings = FavoritesMappings()
    private val repo = SimpleFavoritesRepository(service, mappings)

    @Test
    fun `given mocked data getFavoriteCollections returns said data`() = runBlockingTest {
        val mockData = FavoritesFactory.makeFavoritesApiData(3)
        val expected = List(3) { FavoritesFactory.makeCollection() }

        coEvery { service.getFavoriteCollections() } returns mockData
//        with(mappings) { every { mockData.asCollections() } returns expected }

        val actual = repo.getFavoriteCollections()
        assertThat(actual).isEqualTo(expected)
    }
}