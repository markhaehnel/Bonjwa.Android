package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.*
import androidx.ui.core.TestTag
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.material.surface.Card
import androidx.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.DateTimeUtils
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import xyz.haehnel.bonjwa.ui.TopAppBarVectorButton
import xyz.haehnel.bonjwa.ui.common.ActionBarItem
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

@Model
class ScheduleModel(
    var isLoading: Boolean = false,
    var schedule: MutableList<BonjwaScheduleItem> = mutableListOf(),
    var error: String? = null,
    var initialSelectionDone: Boolean = false
) {

    fun fetchSchedule() {
        error = null
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrievedSchedule = ScheduleRepository().getSchedule().await()
                withContext(Dispatchers.Main) {
                    schedule.clear()
                    schedule.addAll(retrievedSchedule)
                    isLoading = false
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    error = "Fehler beim Laden des Sendeplans."
                    isLoading = false
                }
            }
        }
    }
}

@Composable
fun ScheduleScreen(openDrawer: () -> Unit) {
    val selectedTabIndex = +state { 0 }
    val model = +memo { ScheduleModel() }

    val actionData = listOf(
        ActionBarItem(R.drawable.ic_calendar_today) {
            val c = Calendar.getInstance()
            selectedTabIndex.value =
                weekdays.toList().indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }
        },
        ActionBarItem(R.drawable.ic_refresh) { model.fetchSchedule() }
    )

    +onActive {
        model.fetchSchedule()
    }

    FlexColumn {
        inflexible {
            TopAppBar(
                title = {
                    TestTag("APP_TITLE") {
                        Text(
                            "${+stringResource(R.string.app_name)} ${+stringResource(R.string.schedule)}"
                        )
                    }
                },
                actionData = actionData,
                navigationIcon = {
                    TopAppBarVectorButton(id = R.drawable.ic_hamburger, onClick = openDrawer)
                }
            ) { actionItem ->
                TopAppBarVectorButton(
                    id = actionItem.vectorResource,
                    onClick = { actionItem.action() }
                )
            }
        }
        inflexible {
            WeekdayTabRow(weekdays, selectedTabIndex, onClick = { index ->
                selectedTabIndex.value = index
            })
        }
        expanded(1f) {

            if (model.isLoading) {
                Row(
                    modifier = ExpandedWidth,
                    arrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator((+MaterialTheme.colors()).secondary)
                }
            } else if (model.error != null) {
                Column(
                    modifier = ExpandedWidth,
                    arrangement = Arrangement.Center
                ) {
                    Card(
                        elevation = 4.dp,
                        color = (+MaterialTheme.colors()).error
                    ) {
                        Padding(16.dp) {
                            Column(
                                arrangement = Arrangement.Center
                            ) {
                                Text(text = model.error!!)
                                HeightSpacer(height = 8.dp)
                                Button(
                                    text = +stringResource(R.string.retry),
                                    onClick = { model.fetchSchedule() }
                                )
                            }
                        }
                    }
                }
            } else {
                val weekdaysAsList = weekdays.toList()

                // Select weekday
                val c = Calendar.getInstance()
                if (!model.initialSelectionDone && model.schedule.size > 0) {
                    selectedTabIndex.value =
                        weekdaysAsList.indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }
                    model.initialSelectionDone = true
                }

                val weekdayFromSelectedIndex = weekdaysAsList[selectedTabIndex.value].first

                val weekdayItems = model.schedule.filter {
                    c.time = DateTimeUtils.toDate(it.startDate)
                    c.get(Calendar.DAY_OF_WEEK) == weekdayFromSelectedIndex
                }

                if (weekdayItems.isNotEmpty()) {
                    WeekdayColumn(weekdayItems)
                } else {
                    Column(
                        modifier = ExpandedWidth
                    ) {
                        Card(
                            color = (+MaterialTheme.colors()).primaryVariant,
                            modifier = ExpandedWidth
                        ) {
                            Padding(16.dp) {
                                if (model.schedule.isNullOrEmpty() && c.get(Calendar.DAY_OF_WEEK) == 2) {
                                    Text(+stringResource(R.string.schedule_not_published))
                                } else {
                                    Text(+stringResource(R.string.schedule_not_live))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
