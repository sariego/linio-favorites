package dev.sariego.liniofavorites.app.favorites.presentation.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.filters.LargeTest
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesScreenState
import dev.sariego.liniofavorites.app.favorites.presentation.state.FavoritesViewModel
import dev.sariego.liniofavorites.core.ui.theme.FavoritesAppTheme
import dev.sariego.liniofavorites.test.factory.FavoritesFactory
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random
import kotlin.random.nextInt

@ExperimentalTestApi
@LargeTest
class FavoritesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel = mockk<FavoritesViewModel>()

    @Test
    fun givenCollections_UiShowsCards() {
        val size = Random.nextInt((20..30)) // size of the test
        val mockCollections = List(size) { index ->
            with(FavoritesFactory.makeCollection()) {
                copy(name = "$name $index") // force unique name
            }
        }
        val mockState = FavoritesScreenState.DisplayingFavorites(
            collections = mockCollections,
            products = emptyList()
        )
        every { viewModel.state } returns MutableStateFlow(mockState)

        var numOfColumns = 0 // varies by target device configuration
        composeTestRule.setContent {
            FavoritesAppTheme {
                numOfColumns = NumOfColumnsInFavoritesScreen()
                FavoritesScreen(viewModel)
            }
        }

        mockCollections.forEachIndexed { index, collection ->
            // cut size according to numOfColumns because of custom grid implementation
            composeTestRule.onNode(hasScrollToIndexAction())
                .performScrollToIndex(index / numOfColumns)
            composeTestRule.onNodeWithText(collection.name).assertIsDisplayed()
        }
    }

    @Test
    fun givenProducts_UiShowsCards() {
        val size = Random.nextInt((40..50)) // size of the test
        val mockProducts = List(size) { index ->
            with(FavoritesFactory.makeProduct()) {
                copy(name = "$name $index") // force unique name
            }
        }
        val mockState = FavoritesScreenState.DisplayingFavorites(
            collections = emptyList(),
            products = mockProducts
        )
        every { viewModel.state } returns MutableStateFlow(mockState)

        var numOfColumns = 0 // varies by target device configuration
        composeTestRule.setContent {
            FavoritesAppTheme {
                numOfColumns = NumOfColumnsInFavoritesScreen()
                FavoritesScreen(viewModel)
            }
        }

        mockProducts.forEachIndexed { index, product ->
            // cut size according to numOfColumns because of custom grid implementation
            composeTestRule.onNode(hasScrollToIndexAction())
                .performScrollToIndex(index / numOfColumns)
            composeTestRule.onNodeWithContentDescription(product.name).assertIsDisplayed()
        }
    }

    @Test
    fun givenProducts_UiBuildsHeader() {
        val size = Random.nextInt((1..100)) // size of the test
        val mockProducts = List(size) { FavoritesFactory.makeProduct() }
        val mockState = FavoritesScreenState.DisplayingFavorites(
            collections = emptyList(),
            products = mockProducts
        )
        every { viewModel.state } returns MutableStateFlow(mockState)

        composeTestRule.setContent {
            FavoritesAppTheme {
                FavoritesScreen(viewModel)
            }
        }

        composeTestRule.onNodeWithText("($size)", substring = true).assertExists()
    }

    // todo: check correct sizing of uneven layouts
}
