package xyz.haehnel.bonjwa.ui.events

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.onActive
import androidx.compose.remember
import androidx.ui.animation.Crossfade
import androidx.ui.core.Text
import androidx.ui.material.DrawerState
import androidx.ui.material.Scaffold
import androidx.ui.material.ScaffoldState
import androidx.ui.material.TopAppBar
import androidx.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import xyz.haehnel.bonjwa.ui.BonjwaAppDrawer
import xyz.haehnel.bonjwa.ui.Screen
import xyz.haehnel.bonjwa.ui.TopAppBarVectorButton
import xyz.haehnel.bonjwa.ui.common.ActionBarItem
import xyz.haehnel.bonjwa.ui.common.ErrorCard

sealed class EventsScreenState {
    object Loading: EventsScreenState()
    object Complete: EventsScreenState()
    object Error: EventsScreenState()
}

@Model
class EventsModel(
    var screenState: EventsScreenState = EventsScreenState.Loading,
    var events: MutableList<BonjwaEventItem> = mutableListOf(),
    var error: String = ""
) {

    fun fetchEvents() {
        error = ""
        screenState = EventsScreenState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrievedEvents = ScheduleRepository().getEvents().await()
                withContext(Dispatchers.Main) {
                    events.clear()
                    events.addAll(retrievedEvents)
                    screenState = EventsScreenState.Complete
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    error = "Fehler beim Laden der Events."
                    screenState = EventsScreenState.Error
                }
            }
        }
    }
}

@Composable
fun EventsScreen(
    scaffoldState: ScaffoldState = remember { ScaffoldState() },
    eventsModel: EventsModel = EventsModel()
) {
    val model = remember { eventsModel }

    val actionData = listOf(
        ActionBarItem(R.drawable.ic_refresh) { model.fetchEvents() }
    )

    onActive {
        model.fetchEvents()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            BonjwaAppDrawer(
                currentScreen = Screen.Events,
                closeDrawer = { scaffoldState.drawerState = DrawerState.Closed }
            )
        },
        topAppBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.events)) },
                navigationIcon = {
                    TopAppBarVectorButton(id = R.drawable.ic_hamburger, onClick = {
                        scaffoldState.drawerState = DrawerState.Opened
                    })
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
        },
        bodyContent = {
            Crossfade(model.screenState) { screenState ->
                when (screenState) {
                    is EventsScreenState.Loading -> CircularLoadingIndicator()
                    is EventsScreenState.Complete -> EventItemList(model.events)
                    is EventsScreenState.Error -> ErrorCard(model.error) {
                        model.fetchEvents()
                    }
                }
            }
        }
    )
}
