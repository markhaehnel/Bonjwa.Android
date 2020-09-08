package xyz.haehnel.bonjwa.ui.schedule

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        backgroundColor = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxWidth() then Modifier.preferredHeight(92.dp) then Modifier.padding(
            8.dp
        ),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Row {
            Box(
                modifier = Modifier.preferredWidth(6.dp) then Modifier.fillMaxHeight(),
                backgroundColor = when {
                    item.isRunning -> MaterialTheme.colors.secondary
                    item.cancelled -> MaterialTheme.colors.error
                    else -> Color.Transparent
                }
            )
            Text(
                text = "$startText\n—\n$endText",
                style = MaterialTheme.typography.subtitle2.copy(textAlign = TextAlign.Center),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp) then
                        Modifier.width(40.dp) then
                        Modifier.gravity(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier.fillMaxHeight().wrapContentHeight(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center
            ) {
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
