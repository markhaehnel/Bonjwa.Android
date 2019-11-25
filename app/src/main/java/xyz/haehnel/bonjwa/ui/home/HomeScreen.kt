package xyz.haehnel.bonjwa.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.*
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Card
import androidx.ui.res.imageResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.api.BonjwaScheduleItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import java.time.Instant
import java.time.ZoneOffset
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
    var isLoading: Boolean = true,
    var schedule: MutableList<BonjwaScheduleItem> = mutableListOf()
) {

    fun fetchSchedule() {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
                val retrievedSchedule = ScheduleRepository().getSchedule()
                withContext(Dispatchers.Main) {
                    schedule.clear()
                    schedule.addAll(retrievedSchedule)
                    isLoading = false
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val selectedTabIndex = +state { -1 }

    val refreshImage = +imageResource(R.drawable.ic_refresh)

    val model = +memo { ScheduleModel() }

    +onActive {
        model.fetchSchedule()
    }

    FlexColumn {
        inflexible {
            TopAppBar(
                title = { Text("Bonjwa", style = (+themeTextStyle { h4 })) },
                color = +themeColor { background },
                actionData = listOf(refreshImage)
            ) { actionImage ->
                AppBarIcon(
                    icon = actionImage,
                    onClick = { model.fetchSchedule() })
            }

            TabRow(
                items = weekdays.toList(),
                selectedIndex = selectedTabIndex.value,
                scrollable = true,
                indicatorContainer = @Composable {}
            ) { index, text ->
                Padding(8.dp) {
                    TabCard(
                        text = text.second,
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index }
                    )
                }
            }
        }
        flexible(flex = 1f) {
            if (model.isLoading) {
                Column(
                    crossAxisSize = LayoutSize.Expand,
                    crossAxisAlignment = CrossAxisAlignment.Center,
                    modifier = Spacing(16.dp)
                ) {
                    CircularProgressIndicator(color = +themeColor { secondary })
                }
            } else {
                val weekdaysAsList = weekdays.toList()

                // Select weekday
                if (selectedTabIndex.value == -1) {
                    val c = Calendar.getInstance()
                    selectedTabIndex.value =
                        weekdaysAsList.indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }
                }

                val weekdayFromSelectedIndex = weekdaysAsList[selectedTabIndex.value].first

                val c = Calendar.getInstance()
                val weekdayItems = model.schedule.filter {
                    c.time = Date.from(it.startDate)
                    c.get(Calendar.DAY_OF_WEEK) == weekdayFromSelectedIndex
                }

                if (weekdayItems.isNotEmpty()) {
                    WeekdayColumn(weekdayItems)
                } else {
                    Column(
                        crossAxisSize = LayoutSize.Expand,
                        crossAxisAlignment = CrossAxisAlignment.Stretch,
                        modifier = Spacing(16.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp,
                            color = +themeColor { primaryVariant }) {
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
            crossAxisSize = LayoutSize.Expand,
            crossAxisAlignment = CrossAxisAlignment.Stretch,
            modifier = Spacing(16.dp)
        ) {
            for (item in weekdayItems) {
                ScheduleItemCard(item)
                HeightSpacer(height = 16.dp)
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
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        color = if (isRunning) +themeColor { secondary } else +themeColor { primaryVariant }) {
        Ripple(bounded = true) {
            Padding(16.dp) {
                FlexRow(crossAxisAlignment = CrossAxisAlignment.Start) {
                    inflexible {
                        Column(
                            mainAxisAlignment = MainAxisAlignment.Center,
                            mainAxisSize = LayoutSize.Expand,
                            crossAxisAlignment = CrossAxisAlignment.Center,
                            crossAxisSize = LayoutSize.Expand
                        ) {
                            Text(
                                text = item
                                    .startDate
                                    .atZone(ZoneOffset.systemDefault())
                                    .toLocalTime()
                                    .toString(),
                                style = +themeTextStyle { subtitle2 })
                            Text(text = "—", style = +themeTextStyle { subtitle2 })
                            Text(
                                text = item
                                    .endDate
                                    .atZone(ZoneOffset.systemDefault())
                                    .toLocalTime()
                                    .toString(),
                                style = +themeTextStyle { subtitle2 })
                        }
                        WidthSpacer(width = 16.dp)
                    }
                    expanded(1f) {

                        Column {
                            Text(text = item.title, style = +themeTextStyle { h6 })
                            Text(text = item.caster, style = +themeTextStyle { subtitle1 })
                        }
                    }
                    inflexible {
                        if (isRunning) {
                            WidthSpacer(width = 16.dp)
                            Column(
                                mainAxisAlignment = MainAxisAlignment.Center,
                                mainAxisSize = LayoutSize.Expand,
                                crossAxisAlignment = CrossAxisAlignment.Center,
                                crossAxisSize = LayoutSize.Expand
                            ) {
                                Button(text = "Anschauen", onClick = {
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
fun TabCard(text: String, selected: Boolean, onClick: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(50),
        elevation = 4.dp,
        color = if (selected) (+themeColor { secondary }) else +themeColor { primaryVariant }) {
        Ripple(bounded = true) {
            Clickable(onClick = onClick) {
                Column {
                    Padding(
                        left = 24.dp,
                        top = 8.dp,
                        right = 24.dp,
                        bottom = 8.dp
                    ) {
                        Text(text = text)
                    }
                }
            }
        }

    }
}
