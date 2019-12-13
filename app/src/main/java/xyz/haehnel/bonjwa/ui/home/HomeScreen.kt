package xyz.haehnel.bonjwa.ui.home

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.*
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Card
import androidx.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.api.BonjwaScheduleItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import xyz.haehnel.bonjwa.ui.TopAppBarVectorButton
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

data class ActionItem(@DrawableRes val vectorResource: Int, val action: () -> Unit)

@Composable
fun HomeScreen(openDrawer: () -> Unit) {
    val selectedTabIndex = +state { 0 }
    val model = +memo { ScheduleModel() }

    val actionData = listOf(
        ActionItem(R.drawable.ic_refresh) { model.fetchSchedule() }
    )

    +onActive {
        model.fetchSchedule()
    }

    FlexColumn {
        inflexible {
            TopAppBar(
                title = {
                    Text(
                        "${+stringResource(R.string.app_name)} ${+stringResource(R.string.schedule)}"
                    )
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
        flexible(1f) {
            TabChipRow(weekdays.toList(), selectedTabIndex, onClick = { index ->
                selectedTabIndex.value = index
            })
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
                                    text = "Erneut versuchen",
                                    onClick = { model.fetchSchedule() })
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
                            color = (+MaterialTheme.colors()).primaryVariant
                        ) {
                            Padding(16.dp) {
                                if (model.schedule.isNullOrEmpty() && c.get(Calendar.DAY_OF_WEEK) == 2) {
                                    Text(text = "Der aktuelle Sendeplan wurde noch nicht veröffentlicht.")
                                } else {
                                    Text(text = "Nicht auf Sendung.")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeekdayColumn(weekdayItems: List<BonjwaScheduleItem>) {
    //TODO: extract composable with it's children to separate file

    VerticalScroller {
        Column(
            modifier = ExpandedWidth
        ) {
            for (item in weekdayItems) {
                ScheduleItemCard(item = item)
                Divider()
            }
        }
    }
}

@Composable
fun ScheduleItemCard(item: BonjwaScheduleItem) {
    //TODO: implement cancelled state

    val context = +ambient(ContextAmbient)

    val now = Instant.now()
    val isRunning = item.startDate.isBefore(now) && item.endDate.isAfter(now)

    Card(
        color = when {
            item.cancelled -> (+MaterialTheme.colors()).error
            isRunning -> (+MaterialTheme.colors()).secondaryVariant
            else -> (+MaterialTheme.colors()).primaryVariant
        }
    ) {
        Ripple(bounded = true) {
            Padding(16.dp) {
                FlexRow(crossAxisAlignment = CrossAxisAlignment.Start) {
                    inflexible {
                        Column(
                            modifier = Expanded,
                            arrangement = Arrangement.Center
                        ) {
                            Text(
                                text = item
                                    .startDate
                                    .atZone(ZoneOffset.systemDefault())
                                    .toLocalTime()
                                    .toString(),
                                style = (+MaterialTheme.typography()).subtitle2
                            )
                            Text(text = "—", style = (+MaterialTheme.typography()).subtitle2)
                            Text(
                                text = item
                                    .endDate
                                    .atZone(ZoneOffset.systemDefault())
                                    .toLocalTime()
                                    .toString(),
                                style = (+MaterialTheme.typography()).subtitle2
                            )
                        }
                        WidthSpacer(width = 16.dp)
                    }
                    expanded(1f) {

                        Column {
                            Text(text = item.title, style = (+MaterialTheme.typography()).h6)
                            Text(
                                text = item.caster,
                                style = (+MaterialTheme.typography()).subtitle1
                            )
                        }
                    }

                    inflexible {
                        if (isRunning) {
                            WidthSpacer(width = 16.dp)
                            Column(
                                modifier = Expanded,
                                arrangement = Arrangement.Center
                            ) {
                                Button(
                                    text = "Anschauen",
                                    style = ContainedButtonStyle(color = (+MaterialTheme.colors()).secondary),
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = Uri.parse("https://twitch.tv/bonjwa")
                                        context.startActivity(intent)
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> TabChipRow(
    items: List<T>,
    selectedIndex: State<Int>,
    onClick: (selectedIndex: Int) -> Unit
) {
    TabRow(
        items = weekdays.toList(),
        selectedIndex = selectedIndex.value,
        scrollable = true
    ) { index, text ->
        Tab(
            text = text.second,
            selected = selectedIndex.value == index
        ) {
            selectedIndex.value = index
            onClick(index)
        }
    }
}
