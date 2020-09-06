package xyz.haehnel.bonjwa.ui.events

import androidx.compose.animation.Crossfade
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import xyz.haehnel.bonjwa.ui.BonjwaAppViewModel
import xyz.haehnel.bonjwa.ui.common.ActionBarItem
import xyz.haehnel.bonjwa.ui.common.CircularLoadingIndicator
import xyz.haehnel.bonjwa.ui.common.ErrorCard

sealed class EventsScreenState {
    object Loading : EventsScreenState()
    object Complete : EventsScreenState()
    object Error : EventsScreenState()
}

@Composable
fun EventsScreen(
    appViewModel: BonjwaAppViewModel
) {
    val screenState: MutableState<EventsScreenState> =
        remember { mutableStateOf(EventsScreenState.Loading) }
    val events = remember { mutableStateOf(mutableListOf<BonjwaEventItem>()) }
    val error = remember { mutableStateOf("") }

    val fetchEvents: () -> Unit = {
        error.value = ""
        screenState.value = EventsScreenState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrievedEvents = ScheduleRepository.getEvents()
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


    val screenTitle = "${stringResource(R.string.app_name)} ${stringResource(R.string.events)}"
    val actionData = listOf(
        ActionBarItem(Icons.Filled.Refresh) { fetchEvents() }
    )

    onActive {
        appViewModel.setTopBar(screenTitle, actionData)
        fetchEvents()
    }

    Crossfade(screenState.value) { state ->
        when (state) {
            is EventsScreenState.Loading -> CircularLoadingIndicator()
            is EventsScreenState.Complete -> EventItemList(events.value)
            is EventsScreenState.Error -> ErrorCard(error.value) {
                fetchEvents()
            }
        }
    }
}
