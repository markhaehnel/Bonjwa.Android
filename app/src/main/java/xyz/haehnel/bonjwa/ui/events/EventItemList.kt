package xyz.haehnel.bonjwa.ui.events

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutWidth
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.res.stringResource
import androidx.ui.unit.dp
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem

@Composable
fun EventItemList(events: List<BonjwaEventItem>) {
    if (!events.isNullOrEmpty()) {
        VerticalScroller {
            Column(
                modifier = LayoutWidth.Fill
            ) {
                for (item in events) {
                    EventItemCard(item = item)
                    Divider()
                }
            }
        }

    } else {
        Column {
            Card(
                color = MaterialTheme.colors().primaryVariant,
                modifier = LayoutWidth.Fill
            ) {
                Text(
                    stringResource(R.string.events_empty),
                    modifier = LayoutPadding(16.dp)
                )
            }
        }
    }
}