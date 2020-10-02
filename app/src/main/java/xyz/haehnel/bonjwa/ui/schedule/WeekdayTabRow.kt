package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun WeekdayTabRow(
    items: Map<Int, String>,
    selectedIndex: MutableState<Int>
) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex.value,
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        items.entries.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title.value) },
                selected = selectedIndex.value == index,
                onClick = {
                    selectedIndex.value = index
                }
            )
        }

    }
}
