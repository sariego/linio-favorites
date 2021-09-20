package dev.sariego.liniofavorites.app.favorites.data.repositories

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.liniofavorites.app.favorites.data.mappings.FavoritesMappings
import dev.sariego.liniofavorites.app.favorites.data.sources.LinioFavoritesService
import dev.sariego.liniofavorites.test.factory.FavoritesFactory
import dev.sariego.liniofavorites.test.rule.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class ThrottlingFavoritesRepositoryTest {

    @get:Rule
    val testRule = CoroutinesTestRule()

    private val service = mockk<LinioFavoritesService>(relaxed = true)
    private val mappings = mockk<FavoritesMappings>(relaxed = true)
    private val repo = ThrottlingFavoritesRepository(
        service,
        mappings,
        testRule.testDispatcherProvider,
    )

    @Test
    fun `given mocked data getFavoriteCollections returns said data`() = testRule.runBlockingTest {
        val mockData = FavoritesFactory.makeFavoritesApiData(3)
        val expected = List(3) { FavoritesFactory.makeCollection() }

        coEvery { service.getFavoriteCollections() } returns mockData
        with(mappings) { every { mockData.asCollections() } returns expected }

        val actual = repo.getFavoriteCollections()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given mocked data getFavoriteProducts returns said data`() = testRule.runBlockingTest {
        val mockData = FavoritesFactory.makeFavoritesApiData(3)
        val expected = List(3) { FavoritesFactory.makeProduct() }

        coEvery { service.getFavoriteCollections() } returns mockData
        with(mappings) { every { mockData.asProducts() } returns expected }

        val actual = repo.getFavoriteProducts()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `calling getFavoriteProducts and getFavoriteCollections concurrently only calls service once`() =
        testRule.runBlockingTest {
            repo.getFavoriteCollections()
            repo.getFavoriteProducts()

            coVerify(exactly = 1) { service.getFavoriteCollections() }
        }
}
