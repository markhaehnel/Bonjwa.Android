package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
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
        backgroundColor = when {
            item.cancelled -> MaterialTheme.colors.error
            item.isRunning -> MaterialTheme.colors.secondaryVariant
            else -> MaterialTheme.colors.primaryVariant
        },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "$startText\n—\n$endText",
                style = MaterialTheme.typography.subtitle2.copy(textAlign = TextAlign.Center),
                modifier = Modifier.padding(end = 16.dp) then
                        Modifier.width(40.dp) then
                        Modifier.gravity(Alignment.CenterVertically)
            )
            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (!item.caster.isBlank()) {
                    Text(
                        text = item.caster,
                        style = MaterialTheme.typography.subtitle1,
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
