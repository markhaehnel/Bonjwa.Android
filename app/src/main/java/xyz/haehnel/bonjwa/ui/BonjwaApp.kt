package xyz.haehnel.bonjwa.ui

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacing
import androidx.ui.layout.WidthSpacer
import androidx.ui.material.DrawerState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ModalDrawerLayout
import androidx.ui.material.surface.Surface
import androidx.ui.res.stringResource
import xyz.haehnel.bonjwa.BuildConfig
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.common.AppDrawer
import xyz.haehnel.bonjwa.ui.common.DrawerButton
import xyz.haehnel.bonjwa.ui.common.DrawerInfo
import xyz.haehnel.bonjwa.ui.schedule.ScheduleScreen
import xyz.haehnel.bonjwa.ui.settings.SettingsScreen

@Composable
fun BonjwaApp() {
    MaterialTheme(colors = BonjwaStatus.appTheme) {
        val (drawerState, onDrawerStateChange) = +state { DrawerState.Closed }
        ModalDrawerLayout(
            drawerState = drawerState,
            onStateChange = onDrawerStateChange,
            drawerContent = {
                BonjwaAppDrawer(
                    currentScreen = BonjwaStatus.currentScreen,
                    closeDrawer = { onDrawerStateChange(DrawerState.Closed) }
                )
            },
            bodyContent = { AppContent { onDrawerStateChange(DrawerState.Opened) } }
        )
    }
}

@Composable
private fun AppContent(openDrawer: () -> Unit) {
    Crossfade(BonjwaStatus.currentScreen) { screen ->
        Surface(color = (+MaterialTheme.colors()).background) {
            when (screen) {
                is Screen.Schedule -> ScheduleScreen { openDrawer() }
                is Screen.Settings -> SettingsScreen()
            }
        }
    }
}

@Composable
private fun BonjwaAppDrawer(
    currentScreen: Screen,
    closeDrawer: () -> Unit
) {
    AppDrawer(
        headerContent = {
            Row(modifier = Spacing(16.dp)) {
                VectorImage(id = R.drawable.ic_logo, tint = (+MaterialTheme.colors()).onBackground)
                WidthSpacer(16.dp)
                Column {
                    Text(
                        "${+stringResource(R.string.app_name)} ${+stringResource(R.string.schedule)}",
                        style = (+MaterialTheme.typography()).h6
                    )
                    Text(
                        +stringResource(R.string.app_description),
                        style = (+MaterialTheme.typography()).subtitle1.copy(
                            color = (+MaterialTheme.colors()).onPrimary.copy(alpha = 0.6f)
                        )

                    )
                }
            }
        },

        bodyContent = {
            DrawerButton(
                icon = R.drawable.ic_calendar,
                label = +stringResource(R.string.schedule),
                isSelected = currentScreen == Screen.Schedule
            ) {
                navigateTo(Screen.Schedule)
                closeDrawer()
            }
            DrawerButton(
                icon = R.drawable.ic_settings,
                label = +stringResource(R.string.settings),
                isSelected = currentScreen == Screen.Settings
            ) {
                navigateTo(Screen.Settings)
                closeDrawer()
            }
        },

        footerContent = {
            DrawerInfo(
                text =
                """
                    ${BuildConfig.APPLICATION_ID}
                    ${BuildConfig.VERSION_NAME} (${BuildConfig.BUILD_TYPE})
                    """.trimIndent(),
                icon = R.drawable.ic_info
            )
        }
    )
}
