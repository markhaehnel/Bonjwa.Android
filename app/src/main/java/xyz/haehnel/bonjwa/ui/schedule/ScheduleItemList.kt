package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import org.threeten.bp.DateTimeUtils
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem
import xyz.haehnel.bonjwa.ui.common.MessageCard
import java.util.*

@Composable
fun ScheduleItemList(
    schedule: List<BonjwaScheduleItem>,
    selectedTabIndex: MutableState<Int>
) {
    val initialSelectionDone = remember { mutableStateOf(false) }

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
        if (schedule.isNullOrEmpty() && c.get(Calendar.DAY_OF_WEEK) == 2) {
            MessageCard(
                stringResource(R.string.schedule_not_published),
                R.drawable.ic_no_schedule_published
            )
        } else {
            MessageCard(stringResource(R.string.schedule_not_live), R.drawable.ic_no_schedule_today)
        }
    }
}
