package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.text.style.TextAlign
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem
import xyz.haehnel.bonjwa.ui.darkTheme
import xyz.haehnel.bonjwa.ui.lightTheme

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
                modifier = LayoutPadding(end = 16.dp) + LayoutWidth(40.dp) + LayoutGravity.Center
            )
            Column(
                modifier = LayoutPadding(end = 16.dp)
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

@Preview("Schedule Item Card (Dark)")
@Composable
fun previewDarkScheduleItemCard() {
    MaterialTheme(darkTheme) {
        previewScheduleItemCard()
    }
}

@Preview("Schedule Item Card (Light)")
@Composable
fun previewLightScheduleItemCard() {
    MaterialTheme(lightTheme) {
        previewScheduleItemCard()
    }
}

@Composable
fun previewScheduleItemCard() {
    val scheduleItem = BonjwaScheduleItem(
        "Briefing",
        "Matteo, Leon & Niklas",
        Instant.now(),
        Instant.now().plusSeconds(3600),
        false
    )

    ScheduleItemCard(scheduleItem)
}