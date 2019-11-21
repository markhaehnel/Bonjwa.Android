package xyz.haehnel.bonjwa.ui.home

import androidx.compose.Composable
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
import xyz.haehnel.bonjwa.util.lighten

val weekdays =
    listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag")


@Composable
fun HomeScreen(navigateToSettings: () -> Unit) {

    val selectedTabIndex = +state { 0 }

    FlexColumn {
        inflexible {
            TopAppBar(
                title = { Text("Bonjwa", style = (+themeTextStyle { h4 })) },
                color = +themeColor { background }
            )


            TabRow(
                items = weekdays,
                selectedIndex = selectedTabIndex.value,
                scrollable = true,
                indicatorContainer = @Composable {}
            ) { index, text ->
                Padding(8.dp) {
                    Card(
                        shape = RoundedCornerShape(50),
                        elevation = 4.dp,
                        color = if (index == selectedTabIndex.value) (+themeColor { onPrimary }).lighten() else +themeColor { primaryVariant }) {
                        Ripple(bounded = true) {
                            Clickable(onClick = { selectedTabIndex.value = index }) {
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
            }
        }
        flexible(flex = 1f) {
            WeekdayColumn()
        }
    }
}

@Composable
fun TabCard(title: String, onClick: () -> Unit, selected: Boolean) {

}

@Composable
fun WeekdayColumn() {
    VerticalScroller {
        Column(
            crossAxisSize = LayoutSize.Expand,
            crossAxisAlignment = CrossAxisAlignment.Stretch,
            modifier = Spacing(16.dp)
        ) {
            for (x in 0..11) {
                ScheduleItemCard(
                    "Pog oder Block",
                    "Matteo",
                    "10:00",
                    "12:00"
                )
                HeightSpacer(height = 16.dp)
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
                Column {
                    Text(text = "$timeStart - $timeEnd", style = +themeTextStyle { subtitle2 })
                    Text(text = title, style = +themeTextStyle { h6 })
                    Text(text = caster, style = +themeTextStyle { subtitle1 })
                }
            }
        }
    }
}