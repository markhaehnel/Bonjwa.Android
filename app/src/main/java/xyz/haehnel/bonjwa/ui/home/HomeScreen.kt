package xyz.haehnel.bonjwa.ui.home

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.state
import androidx.compose.unaryPlus
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
private object ScheduleModel {
    init {
        getSchedule()
    }

    var isLoading: Boolean = true
    var schedule: MutableList<BonjwaScheduleItem> = mutableListOf()
    fun getSchedule() {
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

@Composable
fun HomeScreen(navigateToSettings: () -> Unit) {
    val selectedTabIndex = +state { -1 }

    val refreshImage = +imageResource(R.drawable.ic_refresh)

    FlexColumn {
        inflexible {
            TopAppBar(
                title = { Text("Bonjwa", style = (+themeTextStyle { h4 })) },
                color = +themeColor { background },
                actionData = listOf(refreshImage)
            ) { actionImage ->
                AppBarIcon(
                    icon = actionImage,
                    onClick = { ScheduleModel.isLoading = true; ScheduleModel.getSchedule() })
            }

            TabRow(
                items = weekdays.toList(),
                selectedIndex = selectedTabIndex.value,
                scrollable = true,
                indicatorContainer = @Composable {}
            ) { index, text ->
                Padding(8.dp) {
                    WeekdayCard(
                        text = text.second,
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index })
                }
            }
        }
        flexible(flex = 1f) {
            if (ScheduleModel.isLoading) {
                Column(
                    crossAxisSize = LayoutSize.Expand,
                    crossAxisAlignment = CrossAxisAlignment.Center,
                    modifier = Spacing(16.dp)
                ) {
                    CircularProgressIndicator(color = +themeColor { secondary })
                }
            } else {
                val weekdaysAsList = weekdays.toList()
                if (selectedTabIndex.value == -1) {
                    val c = Calendar.getInstance()
                    selectedTabIndex.value =
                        weekdaysAsList.indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }
                }

                val weekdayFromSelectedIndex = weekdaysAsList.get(selectedTabIndex.value).first
                WeekdayColumn(weekdayFromSelectedIndex)
            }
        }
    }
}

@Composable
fun WeekdayColumn(weekday: Int) {
    VerticalScroller {
        Column(
            crossAxisSize = LayoutSize.Expand,
            crossAxisAlignment = CrossAxisAlignment.Stretch,
            modifier = Spacing(16.dp)
        ) {
            val c = Calendar.getInstance()
            val weekdayItems =
                ScheduleModel.schedule.filter { c.setTime(Date.from(it.startDate)); c.get(Calendar.DAY_OF_WEEK) == weekday }

            if (weekdayItems.isNotEmpty()) {
                for (item in weekdayItems) {
                    ScheduleItemCard(
                        item.title,
                        item.caster,
                        item.startDate.atZone(ZoneOffset.systemDefault()).toLocalTime().toString(),
                        item.endDate.atZone(ZoneOffset.systemDefault()).toLocalTime().toString()
                    )
                    HeightSpacer(height = 16.dp)
                }
            } else {
                Text(text = "Nicht auf Sendung.")
            }
        }
    }
}

@Composable
fun ScheduleItemCard(title: String, caster: String, timeStart: String, timeEnd: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        color = +themeColor { primaryVariant }) {
        Ripple(bounded = true) {
            Padding(16.dp) {
                Row(crossAxisAlignment = CrossAxisAlignment.Start) {
                    Column(
                        mainAxisAlignment = MainAxisAlignment.Center,
                        mainAxisSize = LayoutSize.Expand,
                        crossAxisAlignment = CrossAxisAlignment.Center,
                        crossAxisSize = LayoutSize.Expand
                    ) {
                        Text(text = "$timeStart", style = +themeTextStyle { subtitle2 })
                        Text(text = "—", style = +themeTextStyle { subtitle2 })
                        Text(text = "$timeEnd", style = +themeTextStyle { subtitle2 })
                    }
                    WidthSpacer(width = 16.dp)
                    Column {
                        Text(text = title, style = +themeTextStyle { h6 })
                        Text(text = caster, style = +themeTextStyle { subtitle1 })
                    }
                }
            }
        }
    }
}

@Composable
fun WeekdayCard(text: String, selected: Boolean, onClick: () -> Unit) {
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