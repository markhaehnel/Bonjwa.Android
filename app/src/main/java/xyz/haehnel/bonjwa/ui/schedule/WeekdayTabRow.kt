package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.Composable
import androidx.compose.State
import androidx.ui.material.Tab
import androidx.ui.material.TabRow
import java.util.*

@Composable
fun WeekdayTabRow(
    items: Map<Int, String>,
    selectedIndex: State<Int>,
    onClick: (selectedIndex: Int) -> Unit
) {

    val c = Calendar.getInstance()

    TabRow(
        items = items.values.toList(),
        selectedIndex = selectedIndex.value,
        scrollable = true
    ) { index, text ->

        val todayIndex = items.toList().indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }

        val weekdayText = if (todayIndex == index) "• $text" else text

        Tab(
            text = weekdayText,
            selected = selectedIndex.value == index
        ) {
            selectedIndex.value = index
            onClick(index)
        }
    }
}