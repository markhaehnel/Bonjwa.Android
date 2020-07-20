package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.Divider
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

@Composable
fun WeekdayColumn(weekdayItems: List<BonjwaScheduleItem>) {
    VerticalScroller {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (item in weekdayItems) {
                ScheduleItemCard(item = item)
                Divider()
            }
        }
    }
}
