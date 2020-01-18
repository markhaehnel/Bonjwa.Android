package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.Composable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.ExpandedWidth
import androidx.ui.material.Divider
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

@Composable
fun WeekdayColumn(weekdayItems: List<BonjwaScheduleItem>) {
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
