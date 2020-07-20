package xyz.haehnel.bonjwa.ui

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import xyz.haehnel.bonjwa.BuildConfig
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.common.AppDrawer
import xyz.haehnel.bonjwa.ui.common.DrawerButton
import xyz.haehnel.bonjwa.ui.common.DrawerInfo
import xyz.haehnel.bonjwa.ui.events.EventsScreen
import xyz.haehnel.bonjwa.ui.schedule.ScheduleScreen
import xyz.haehnel.bonjwa.ui.settings.SettingsScreen

@Composable
fun BonjwaApp(
    navigationViewModel: NavigationViewModel
) {
    MaterialTheme(colors = BonjwaStatus.appTheme) {
        AppContent(navigationViewModel)
    }
}

@Composable
private fun AppContent(
    navigationViewModel: NavigationViewModel
) {
    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Schedule -> ScheduleScreen(
                    navigateTo = navigationViewModel::navigateTo
                )
                is Screen.Events -> EventsScreen(
                    navigateTo = navigationViewModel::navigateTo
                )
                is Screen.Settings -> SettingsScreen(
                    navigateTo = navigationViewModel::navigateTo
                )
            }
        }
    }
}

@Composable
fun BonjwaAppDrawer(
    navigateTo: (Screen) -> Unit,
    currentScreen: Screen,
    closeDrawer: () -> Unit
) {
    AppDrawer(
        headerContent = {
            Row(modifier = Modifier.padding(top = 24.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)) {
                Icon(vectorResource(id = R.drawable.ic_logo), tint = MaterialTheme.colors.onBackground)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = "${stringResource(R.string.app_name)} ${stringResource(R.string.schedule)}",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        stringResource(R.string.app_description),
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        },
        bodyContent = {
            DrawerButton(
                icon = R.drawable.ic_calendar,
                label = stringResource(R.string.schedule),
                isSelected = currentScreen == Screen.Schedule
            ) {
                navigateTo(Screen.Schedule)
                closeDrawer()
            }
            DrawerButton(
                icon = R.drawable.ic_events,
                label = stringResource(R.string.events),
                isSelected = currentScreen == Screen.Events
            ) {
                navigateTo(Screen.Events)
                closeDrawer()
            }
        },
        footerContent = {
            DrawerButton(
                icon = R.drawable.ic_settings,
                label = stringResource(R.string.settings),
                isSelected = currentScreen == Screen.Settings
            ) {
                navigateTo(Screen.Settings)
                closeDrawer()
            }

            DrawerInfo(
                text = "Version ${BuildConfig.VERSION_NAME} (${BuildConfig.BUILD_TYPE})",
                icon = R.drawable.ic_info
            )
        }

    )
}
