package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.core.Text
import androidx.ui.material.Tab
import androidx.ui.material.TabRow
import java.util.*

@Composable
fun WeekdayTabRow(
    items: Map<Int, String>,
    selectedIndex: MutableState<Int>
) {

    val c = Calendar.getInstance()

    TabRow(
        items = items.values.toList(),
        selectedIndex = selectedIndex.value,
        scrollable = true
    ) { index, text ->

        val todayIndex = items.toList().indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }

        Tab(
            text = { Text(text) },
            selected = selectedIndex.value == index,
            onSelected = {
                selectedIndex.value = index
            }
        )
    }
}