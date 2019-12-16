package xyz.haehnel.bonjwa

import androidx.compose.Composable
import androidx.ui.core.semantics.getOrNull
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.semantics.SemanticsProperties
import androidx.ui.test.ComposeTestRule
import androidx.ui.test.SemanticsNodeInteraction
import androidx.ui.test.findAll
import xyz.haehnel.bonjwa.ui.BonjwaApp
import xyz.haehnel.bonjwa.ui.BonjwaStatus
import xyz.haehnel.bonjwa.ui.Screen

/**
 * Launches the app from a test context
 */
fun ComposeTestRule.launchBonjwaApp() {
    setContent {
        BonjwaStatus.resetState()
        BonjwaApp()
    }
}

/**
 * Resets the state of the app. Needs to be executed in Compose code (within a frame)
 */
fun BonjwaStatus.resetState() {
    currentScreen = Screen.Schedule
}

/**
 * Helper method that can be used to test Jetnews UI Composables in isolation
 */
fun ComposeTestRule.setMaterialContent(children: @Composable() () -> Unit) {
    setContent {
        MaterialTheme {
            Surface(children = children)
        }
    }
}

/**
 * Workarounds, these functions should be removed when UI testing improves
 */

fun workForComposeToBeIdle() {
    // Temporary workaround - use waitForIdle in dev04
    Thread.sleep(500)
}

fun findAllByText(text: String, ignoreCase: Boolean = false): List<SemanticsNodeInteraction> {
    return findAll {
        getOrNull(SemanticsProperties.AccessibilityLabel).equals(text, ignoreCase)
    }
}