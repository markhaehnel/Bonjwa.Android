package xyz.haehnel.bonjwa.ui.events

import androidx.compose.*
import androidx.lifecycle.ViewModel
import androidx.ui.animation.Crossfade
import androidx.ui.foundation.Text
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
import xyz.haehnel.bonjwa.ui.common.CircularLoadingIndicator
import xyz.haehnel.bonjwa.ui.common.ErrorCard
import kotlin.collections.setValue

sealed class EventsScreenState {
    object Loading: EventsScreenState()
    object Complete: EventsScreenState()
    object Error: EventsScreenState()
}

@Composable
fun EventsScreen(
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState = remember { ScaffoldState() }
) {
    val screenState : MutableState<EventsScreenState> = state { EventsScreenState.Loading }
    val events  = state { mutableListOf<BonjwaEventItem>() }
    val error = state { "" }

    val fetchEvents: () -> Unit = {
        error.value = ""
        screenState.value = EventsScreenState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrievedEvents = ScheduleRepository.getEvents().await()
                withContext(Dispatchers.Main) {
                    events.value.clear()
                    events.value.addAll(retrievedEvents)
                    screenState.value = EventsScreenState.Complete
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    error.value = "Fehler beim Laden der Events."
                    screenState.value = EventsScreenState.Error
                }
            }
        }
    }

    val actionData = listOf(
        ActionBarItem(R.drawable.ic_refresh) { fetchEvents() }
    )

    onActive {
        fetchEvents()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            BonjwaAppDrawer(
                navigateTo = navigateTo,
                currentScreen = Screen.Events,
                closeDrawer = { scaffoldState.drawerState = DrawerState.Closed }
            )
        },
        topBar = {
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
            Crossfade(screenState.value) { screenState ->
                when (screenState) {
                    is EventsScreenState.Loading -> CircularLoadingIndicator()
                    is EventsScreenState.Complete -> EventItemList(events.value)
                    is EventsScreenState.Error -> ErrorCard(error.value) {
                        fetchEvents()
                    }
                }
            }
        }
    )
}
