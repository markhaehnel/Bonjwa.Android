package xyz.haehnel.bonjwa.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.events.EventsScreen
import xyz.haehnel.bonjwa.ui.schedule.ScheduleScreen
import xyz.haehnel.bonjwa.ui.settings.SettingsScreen


@Composable
fun BonjwaApp(
    appViewModel: BonjwaAppViewModel
) {

    val scaffoldState = rememberScaffoldState()

    MaterialTheme(colors = themeList[appViewModel.appThemeIndex.value]) {

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            Text(
                                modifier = Modifier.testTag("APP_TITLE"),
                                text = appViewModel.topBarTitle.value
                            )
                        },
                        actions = {
                            appViewModel.topBarActions.forEach {
                                IconButton(onClick = { it.action() }) {
                                    Icon(it.icon)
                                }
                            }
                        }
                    )
                }

            },
            bodyContent = {
                Crossfade(appViewModel.currentScreen) { screen ->
                    Surface(color = MaterialTheme.colors.background) {
                        when (screen.value) {
                            is Screen.Schedule -> ScheduleScreen(appViewModel)
                            is Screen.Events -> EventsScreen(appViewModel)
                            is Screen.Settings -> SettingsScreen(appViewModel)
                        }
                    }
                }
            },
            bottomBar = {
                var selectedItem by remember { mutableStateOf(0) }
                val items = listOf(
                    Triple(
                        Screen.Schedule,
                        stringResource(R.string.schedule),
                        Icons.Filled.DateRange
                    ),
                    Triple(
                        Screen.Events,
                        stringResource(R.string.events),
                        Icons.Filled.Notifications
                    ),
                    Triple(
                        Screen.Settings,
                        stringResource(R.string.settings),
                        Icons.Filled.Settings
                    ),
                )

                BottomNavigation {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = { Icon(item.third) },
                            label = { Text(item.second) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                appViewModel.setCurrentScreen(item.first)
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                if (appViewModel.fabAction.value != null) {
                    FloatingActionButton(onClick = appViewModel.fabAction.value!!.action) {
                        Icon(appViewModel.fabAction.value!!.icon)
                    }
                }
            }
        )
    }
}

