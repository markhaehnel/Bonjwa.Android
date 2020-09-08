package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

@Composable
fun WeekdayColumn(weekdayItems: List<BonjwaScheduleItem>) {
    ScrollableColumn(
        modifier = Modifier.fillMaxWidth() then Modifier.padding(top = 8.dp)
    ) {
        for (item in weekdayItems) {
            ScheduleItemCard(item = item)
        }
    }
}
