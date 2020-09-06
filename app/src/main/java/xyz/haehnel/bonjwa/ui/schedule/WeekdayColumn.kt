package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

@Composable
fun WeekdayColumn(weekdayItems: List<BonjwaScheduleItem>) {
    ScrollableColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (item in weekdayItems) {
            ScheduleItemCard(item = item)
            Divider()
        }
    }
}
