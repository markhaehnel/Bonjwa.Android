package xyz.haehnel.bonjwa.ui.events

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.ui.common.MessageCard

@Composable
fun EventItemList(events: List<BonjwaEventItem>) {
    if (!events.isNullOrEmpty()) {
        ScrollableColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (item in events) {
                EventItemCard(item)
                Divider()
            }
        }
    } else {
        MessageCard(
            stringResource(R.string.events_empty),
            drawable = R.drawable.ic_no_events
        )
    }
}