package xyz.haehnel.bonjwa.ui.schedule

import android.content.Intent
import android.net.Uri
import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.res.stringResource
import androidx.ui.text.style.TextAlign
import androidx.ui.text.style.TextOverflow
import androidx.ui.unit.dp
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

val BONJWA_CHANNEL_URL : Uri = Uri.parse("https://twitch.tv/bonjwa")

@Composable
fun ScheduleItemCard(item: BonjwaScheduleItem) {
    val context = ContextAmbient.current

    val now = Instant.now()
    val isRunning = item.startDate.isBefore(now) && item.endDate.isAfter(now)

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
            isRunning -> (MaterialTheme.colors()).secondaryVariant
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
                Text(
                    text = item.caster,
                    style = (MaterialTheme.typography()).subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (isRunning) {
                    Button(
                        backgroundColor = (MaterialTheme.colors()).secondary,
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = BONJWA_CHANNEL_URL
                            context.startActivity(intent)
                        },
                        modifier = LayoutWidth.Fill + LayoutPadding(top = 8.dp)
                    ) {
                        Text(stringResource(R.string.schedule_watch))
                    }
                }
            }
        }

    }
}