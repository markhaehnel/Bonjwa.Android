package xyz.haehnel.bonjwa.ui.schedule

import android.content.Intent
import android.net.Uri
import androidx.compose.*
import androidx.ui.animation.Crossfade
import androidx.ui.core.ContextAmbient
import androidx.ui.core.TestTag
import androidx.ui.core.Text
import androidx.ui.layout.Column
import androidx.ui.material.*
import androidx.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import xyz.haehnel.bonjwa.ui.BonjwaAppDrawer
import xyz.haehnel.bonjwa.ui.Screen
import xyz.haehnel.bonjwa.ui.TopAppBarVectorButton
import xyz.haehnel.bonjwa.ui.VectorImage
import xyz.haehnel.bonjwa.ui.common.ActionBarItem
import xyz.haehnel.bonjwa.ui.common.CircularLoadingIndicator
import xyz.haehnel.bonjwa.ui.common.ErrorCard
import java.util.*

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

@Model
class ScheduleModel(
    var screenState: ScheduleScreenState = ScheduleScreenState.Loading,
    var schedule: MutableList<BonjwaScheduleItem> = mutableListOf(),
    var error: String = ""
) {

    fun fetchSchedule() {
        error = ""
        screenState = ScheduleScreenState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrievedSchedule = ScheduleRepository.getSchedule().await()
                withContext(Dispatchers.Main) {
                    schedule.clear()
                    schedule.addAll(retrievedSchedule)
                    screenState = ScheduleScreenState.Complete
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    error = "Fehler beim Laden des Sendeplans."
                    screenState = ScheduleScreenState.Error
                }
            }
        }
    }
}

@Composable
fun ScheduleScreen(
    scaffoldState: ScaffoldState = remember { ScaffoldState() },
    model: ScheduleModel = remember { ScheduleModel() }
) {
    val selectedTabIndex = state { 0 }

    val actionData = listOf(
        ActionBarItem(R.drawable.ic_calendar_today) {
            val c = Calendar.getInstance()
            selectedTabIndex.value =
                weekdays.toList().indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }
        },
        ActionBarItem(R.drawable.ic_refresh) { model.fetchSchedule() }
    )

    onCommit {
        model.fetchSchedule()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            BonjwaAppDrawer(
                currentScreen = Screen.Schedule,
                closeDrawer = { scaffoldState.drawerState = DrawerState.Closed }
            )
        },
        topAppBar = {
            Column {
                TopAppBar(
                    title = {
                        TestTag("APP_TITLE") {
                            Text(
                                "${stringResource(R.string.app_name)} ${stringResource(R.string.schedule)}"
                            )
                        }
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
            Crossfade(model.screenState) { screenState ->
                when (screenState) {
                    is ScheduleScreenState.Loading -> CircularLoadingIndicator()
                    is ScheduleScreenState.Complete -> ScheduleItemList(model.schedule, selectedTabIndex)
                    is ScheduleScreenState.Error -> ErrorCard(model.error) {
                        model.fetchSchedule()
                    }
                }
            }
        },
        floatingActionButton = {
            if (model.schedule.any { it.isRunning }) {
                val context = ContextAmbient.current
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = BONJWA_CHANNEL_URL
                        context.startActivity(intent)
                    },
                    color = MaterialTheme.colors().secondary
                ) {
                    VectorImage(
                        id = R.drawable.ic_play
                    )
                }
            }
        }
    )
}
