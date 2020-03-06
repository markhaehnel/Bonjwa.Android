package xyz.haehnel.bonjwa

import androidx.ui.test.assertIsDisplayed
import androidx.ui.test.createComposeRule
import androidx.ui.test.findByTag
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
        findByTag("APP_TITLE").apply {
            assertIsDisplayed()
        }
    }
}
