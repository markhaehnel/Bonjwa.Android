package xyz.haehnel.bonjwa.ui.events

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.Divider
import androidx.ui.res.stringResource
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.ui.common.MessageCard

@Composable
fun EventItemList(events: List<BonjwaEventItem>) {
    if (!events.isNullOrEmpty()) {
        VerticalScroller {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (item in events) {
                    EventItemCard(item)
                    Divider()
                }
            }
        }
    } else {
        MessageCard(
            stringResource(R.string.events_empty),
            drawable = R.drawable.ic_no_events
        )
    }
}