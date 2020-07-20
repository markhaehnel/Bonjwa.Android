package xyz.haehnel.bonjwa.ui.schedule

import android.content.Intent
import android.net.Uri
import androidx.compose.*
import androidx.ui.animation.Crossfade
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.testTag
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.material.*
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import xyz.haehnel.bonjwa.ui.BonjwaAppDrawer
import xyz.haehnel.bonjwa.ui.Screen
import xyz.haehnel.bonjwa.ui.TopAppBarVectorButton
import xyz.haehnel.bonjwa.ui.common.ActionBarItem
import xyz.haehnel.bonjwa.ui.common.CircularLoadingIndicator
import xyz.haehnel.bonjwa.ui.common.ErrorCard
import xyz.haehnel.bonjwa.ui.events.EventsScreenState
import java.util.*
import kotlin.reflect.KFunction1

val weekdays =
    mapOf(
        2 to "Montag",
        3 to "Dienstag",
        4 to "Mittwoch",
        5 to "Donnerstag",
        6 to "Freitag",
        7 to "Samstag",
        1 to "Sonntag"
    )

val BONJWA_CHANNEL_URL : Uri = Uri.parse("https://twitch.tv/bonjwa")

sealed class ScheduleScreenState {
    object Loading : ScheduleScreenState()
    object Complete : ScheduleScreenState()
    object Error : ScheduleScreenState()
}

@Composable
fun ScheduleScreen(
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState = remember { ScaffoldState() }
) {
    val screenState : MutableState<ScheduleScreenState> = state { ScheduleScreenState.Loading }
    val schedule  = state { mutableListOf<BonjwaScheduleItem>() }
    val error = state { "" }

    val fetchSchedule : () -> Unit = {
        error.value = ""
        screenState.value = ScheduleScreenState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrievedSchedule = ScheduleRepository.getSchedule().await()
                withContext(Dispatchers.Main) {
                    schedule.value.clear()
                    schedule.value.addAll(retrievedSchedule)
                    screenState.value = ScheduleScreenState.Complete
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    error.value = "Fehler beim Laden des Sendeplans."
                    screenState.value = ScheduleScreenState.Error
                }
            }
        }
    }

    val selectedTabIndex = state { 0 }

    val actionData = listOf(
        ActionBarItem(R.drawable.ic_calendar_today) {
            val c = Calendar.getInstance()
            selectedTabIndex.value =
                weekdays.toList().indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }
        },
        ActionBarItem(R.drawable.ic_refresh) { fetchSchedule() }
    )

    onCommit {
        fetchSchedule()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            BonjwaAppDrawer(
                navigateTo = navigateTo,
                currentScreen = Screen.Schedule,
                closeDrawer = { scaffoldState.drawerState = DrawerState.Closed }
            )
        },
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.testTag("APP_TITLE"),
                            text = "${stringResource(R.string.app_name)} ${stringResource(R.string.schedule)}"
                        )
                    },
                    navigationIcon = {
                        TopAppBarVectorButton(
                            id = R.drawable.ic_hamburger,
                            onClick = {
                                scaffoldState.drawerState = DrawerState.Opened
                            }
                        )
                    },
                    actions = {
                        actionData.forEach {
                            TopAppBarVectorButton(
                                id = it.vectorResource,
                                onClick = { it.action() }
                            )
                        }
                    }
                )
                WeekdayTabRow(weekdays, selectedTabIndex)
            }

        },
        bodyContent = {
            Crossfade(screenState.value) { screenState ->
                when (screenState) {
                    is ScheduleScreenState.Loading -> CircularLoadingIndicator()
                    is ScheduleScreenState.Complete -> ScheduleItemList(schedule.value, selectedTabIndex)
                    is ScheduleScreenState.Error -> ErrorCard(error.value) {
                        fetchSchedule()
                    }
                }
            }
        },
        floatingActionButton = {
            if (schedule.value.any { it.isRunning }) {
                val context = ContextAmbient.current
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = BONJWA_CHANNEL_URL
                        context.startActivity(intent)
                    },
                    backgroundColor = MaterialTheme.colors.secondary
                ) {
                    Icon(
                        asset = vectorResource(id = R.drawable.ic_play)
                    )
                }
            }
        }
    )
}
