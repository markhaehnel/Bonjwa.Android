package xyz.haehnel.bonjwa.ui.schedule

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import xyz.haehnel.bonjwa.ui.BonjwaAppViewModel
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

val BONJWA_CHANNEL_URL: Uri = Uri.parse("https://twitch.tv/bonjwa")

sealed class ScheduleScreenState {
    object Loading : ScheduleScreenState()
    object Complete : ScheduleScreenState()
    object Error : ScheduleScreenState()
}

@Composable
fun ScheduleScreen(
    appViewModel: BonjwaAppViewModel
) {
    val screenState: MutableState<ScheduleScreenState> =
        remember { mutableStateOf(ScheduleScreenState.Loading) }
    val schedule = remember { mutableStateOf(mutableListOf<BonjwaScheduleItem>()) }
    val error = remember { mutableStateOf("") }
    val isAnyRunning = remember { mutableStateOf(false) }

    val fetchSchedule: () -> Unit = {
        error.value = ""
        screenState.value = ScheduleScreenState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrievedSchedule = ScheduleRepository.getSchedule()
                withContext(Dispatchers.Main) {
                    schedule.value.clear()
                    schedule.value.addAll(retrievedSchedule)
                    isAnyRunning.value = retrievedSchedule.any { it.isRunning }
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

    val selectedTabIndex = remember { mutableStateOf(0) }

    val screenTitle = "${stringResource(R.string.app_name)} ${stringResource(R.string.schedule)}"
    val actionData = listOf(
        ActionBarItem(Icons.Filled.DateRange) {
            val c = Calendar.getInstance()
            selectedTabIndex.value =
                weekdays.toList().indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }
        },
        ActionBarItem(Icons.Filled.Refresh) { fetchSchedule() }
    )
    val context = ContextAmbient.current
    val playStreamActionBarItem = ActionBarItem(Icons.Filled.PlayArrow) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = BONJWA_CHANNEL_URL
        context.startActivity(intent)
    }

    onActive {
        fetchSchedule()
    }

    onCommit {
        val fabItem = if (isAnyRunning.value) playStreamActionBarItem else null
        appViewModel.setTopBar(screenTitle, actionData, fabItem)
    }


    Column {

        WeekdayTabRow(weekdays, selectedTabIndex)
        Crossfade(screenState.value) { state ->
            when (state) {
                is ScheduleScreenState.Loading -> CircularLoadingIndicator()
                is ScheduleScreenState.Complete -> ScheduleItemList(
                    schedule.value,
                    selectedTabIndex
                )
                is ScheduleScreenState.Error -> ErrorCard(error.value) {
                    fetchSchedule()
                }
            }
        }
    }
}
