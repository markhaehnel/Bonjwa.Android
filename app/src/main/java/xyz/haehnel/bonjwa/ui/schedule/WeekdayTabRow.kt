package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.core.Text
import androidx.ui.material.Tab
import androidx.ui.material.TabRow

@Composable
fun WeekdayTabRow(
    items: Map<Int, String>,
    selectedIndex: MutableState<Int>
) {
    TabRow(
        items = items.values.toList(),
        selectedIndex = selectedIndex.value,
        scrollable = true
    ) { index, text ->

        Tab(
            text = { Text(text) },
            selected = selectedIndex.value == index,
            onSelected = {
                selectedIndex.value = index
            }
        )
    }
}