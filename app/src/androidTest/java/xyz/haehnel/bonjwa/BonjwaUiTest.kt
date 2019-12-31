package xyz.haehnel.bonjwa

import androidx.ui.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BonjwaUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.launchBonjwaApp()
    }

    @Test
    fun app_launches() {
        // findByText("Bonjwa Sendeplan").assertIsDisplayed()
        findByTag("REFRESH_ACTION").apply {
            assertIsVisible()
            assertHasClickAction()
        }
    }
}
