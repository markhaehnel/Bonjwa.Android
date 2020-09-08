package xyz.haehnel.bonjwa.ui.events

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.ui.common.*

@Composable
fun EventItemList(events: List<BonjwaEventItem>) {
    if (!events.isNullOrEmpty()) {
        ScrollableColumn(
            modifier = Modifier.fillMaxWidth() then Modifier.padding(top = 4.dp)
        ) {
            for (item in events) {
                EventItemCard(item)
            }
        }
    } else {
        MessageCard(
            stringResource(R.string.events_empty),
            drawable = R.drawable.ic_no_events
        )
    }
}