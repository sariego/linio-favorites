package dev.sariego.liniofavorites.app.favorites.domain.usecases

import androidx.test.filters.SmallTest
import dev.sariego.liniofavorites.app.favorites.domain.repositories.FavoritesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class UseCasesTest {

    private val repo = mockk<FavoritesRepository>(relaxed = true)

    @Test
    fun `GetFavoriteCollections uses repository implementation`() = runBlockingTest {
        val usecase = GetFavoriteCollections(repo)

        usecase()
        coVerify { repo.getFavoriteCollections() }
    }

    @Test
    fun `GetFavoriteProducts uses repository implementation`() = runBlockingTest {
        val usecase = GetFavoriteProducts(repo)

        usecase()
        coVerify { repo.getFavoriteProducts() }
    }
}
