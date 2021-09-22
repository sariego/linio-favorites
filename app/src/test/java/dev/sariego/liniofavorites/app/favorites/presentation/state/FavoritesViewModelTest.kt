package dev.sariego.liniofavorites.app.favorites.presentation.state

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.liniofavorites.app.favorites.domain.usecases.GetFavoriteCollections
import dev.sariego.liniofavorites.app.favorites.domain.usecases.GetFavoriteProducts
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesScreenState.DisplayingFavorites
import dev.sariego.liniofavorites.test.factory.FavoritesFactory
import dev.sariego.liniofavorites.test.rule.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class FavoritesViewModelTest {

    @get:Rule
    val testRule = CoroutinesTestRule()

    private val getCollections = mockk<GetFavoriteCollections>(relaxed = true)
    private val getProducts = mockk<GetFavoriteProducts>(relaxed = true)
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        viewModel = FavoritesViewModel(getCollections, getProducts)
    }

    @Test
    fun `given mocked data when data loads state should reflect Loading then Displaying`() =
        testRule.runBlockingTest {
            val mockedCollections = List(3) { FavoritesFactory.makeCollection() }
            val mockedProducts = List(3) { FavoritesFactory.makeProduct() }
            val expectedStates = listOf(
                FavoritesScreenState.Loading,
                DisplayingFavorites(mockedCollections, mockedProducts)
            )

            coEvery { getCollections() } coAnswers {
                delay(SMALL_DELAY) // this delay is instant in tests! (thanks to CoroutinesTestRule :D)
                mockedCollections
            }

            coEvery { getProducts() } coAnswers {
                delay(SMALL_DELAY)
                mockedProducts
            }

            val actualStates = viewModel.state.take(2).toList()
            assertThat(actualStates).isEqualTo(expectedStates)
        }

    @Test
    fun `given mocked data when data is instant state should just be Displaying`() =
        testRule.runBlockingTest {
            // this case happens for example on configuration changes (when the phone rotates)

            val mockedCollections = List(3) { FavoritesFactory.makeCollection() }
            val mockedProducts = List(3) { FavoritesFactory.makeProduct() }
            val expected = DisplayingFavorites(mockedCollections, mockedProducts)

            coEvery { getCollections() } returns mockedCollections
            coEvery { getProducts() } returns mockedProducts

            val actual = viewModel.state.first()
            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `when Collections load fails state should reflect Loading then Error`() =
        testRule.runBlockingTest {
            val error = Throwable()
            val expectedStates = listOf(
                FavoritesScreenState.Loading,
                FavoritesScreenState.DisplayingError(cause = error)
            )

            coEvery { getCollections() } coAnswers {
                delay(SMALL_DELAY)
                throw error
            }

            val actualStates = viewModel.state.take(2).toList()
            assertThat(actualStates).isEqualTo(expectedStates)
        }

    @Test
    fun `when Products load fails state should reflect Loading then Error`() =
        testRule.runBlockingTest {
            val error = Throwable()
            val expectedStates = listOf(
                FavoritesScreenState.Loading,
                FavoritesScreenState.DisplayingError(cause = error)
            )

            coEvery { getProducts() } coAnswers {
                delay(SMALL_DELAY)
                throw error
            }

            val actualStates = viewModel.state.take(2).toList()
            assertThat(actualStates).isEqualTo(expectedStates)
        }

    private companion object {
        const val SMALL_DELAY = 1_000L
    }
}
