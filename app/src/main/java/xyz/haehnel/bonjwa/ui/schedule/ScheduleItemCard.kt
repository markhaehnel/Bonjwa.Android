package xyz.haehnel.bonjwa.ui.schedule

import android.net.Uri
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.text.style.TextAlign
import androidx.ui.text.style.TextOverflow
import androidx.ui.unit.dp
import org.threeten.bp.ZoneOffset
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

val BONJWA_CHANNEL_URL : Uri = Uri.parse("https://twitch.tv/bonjwa")

@Composable
fun ScheduleItemCard(item: BonjwaScheduleItem) {

    val startText = item
        .startDate
        .atZone(ZoneOffset.systemDefault())
        .toLocalTime()
        .toString()

    val endText = item
        .endDate
        .atZone(ZoneOffset.systemDefault())
        .toLocalTime()
        .toString()

    Card(
        color = when {
            item.cancelled -> (MaterialTheme.colors()).error
            item.isRunning -> (MaterialTheme.colors()).secondaryVariant
            else -> (MaterialTheme.colors()).primaryVariant
        },
        modifier = LayoutWidth.Fill
    ) {
        Row(modifier = LayoutPadding(16.dp)) {
            Text(
                text = "$startText\n—\n$endText",
                style = (MaterialTheme.typography()).subtitle2.copy(textAlign = TextAlign.Center),
                modifier = LayoutPadding(right = 16.dp) + LayoutWidth(40.dp) + LayoutGravity.Center
            )
            Column(
                modifier = LayoutPadding(right = 16.dp)
            ) {
                Text(
                    text = item.title,
                    style = (MaterialTheme.typography()).h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!item.caster.isBlank()) {
                    Text(
                        text = item.caster,
                        style = (MaterialTheme.typography()).subtitle1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }
}