package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.ui.core.Text
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutWidth
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.res.stringResource
import androidx.ui.unit.dp
import org.threeten.bp.DateTimeUtils
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem
import java.util.*

@Composable
fun ScheduleItemList(
    schedule: List<BonjwaScheduleItem>,
    selectedTabIndex: MutableState<Int>
) {
    val initialSelectionDone = state { false }

    val weekdaysAsList = weekdays.toList()

    // Select weekday
    val c = Calendar.getInstance()
    if (!initialSelectionDone.value && schedule.isNotEmpty()) {
        selectedTabIndex.value =
            weekdaysAsList.indexOfFirst { it.first == c.get(Calendar.DAY_OF_WEEK) }
        initialSelectionDone.value = true
    }

    val weekdayFromSelectedIndex = weekdaysAsList[selectedTabIndex.value].first

    val weekdayItems = schedule.filter {
        c.time = DateTimeUtils.toDate(it.startDate)
        c.get(Calendar.DAY_OF_WEEK) == weekdayFromSelectedIndex
    }

    if (weekdayItems.isNotEmpty()) {
        WeekdayColumn(weekdayItems)

    } else {
        Column(
            modifier = LayoutWidth.Fill
        ) {
            Card(
                color = (MaterialTheme.colors()).primaryVariant,
                modifier = LayoutWidth.Fill + LayoutPadding(16.dp)
            ) {
                if (schedule.isNullOrEmpty() && c.get(Calendar.DAY_OF_WEEK) == 2) {
                    Text(stringResource(R.string.schedule_not_published))
                } else {
                    Text(stringResource(R.string.schedule_not_live))
                }
            }
        }
    }
}
