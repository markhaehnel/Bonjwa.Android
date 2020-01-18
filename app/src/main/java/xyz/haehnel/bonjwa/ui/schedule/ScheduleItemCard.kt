package xyz.haehnel.bonjwa.ui.schedule

import android.content.Intent
import android.net.Uri
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.unaryPlus
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.ContainedButtonStyle
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Card
import androidx.ui.res.stringResource
import androidx.ui.text.ParagraphStyle
import androidx.ui.text.style.TextAlign
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

val BONJWA_CHANNEL_URL : Uri = Uri.parse("https://twitch.tv/bonjwa")

@Composable
fun ScheduleItemCard(item: BonjwaScheduleItem) {
    val context = +ambient(ContextAmbient)

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
            item.cancelled -> (+MaterialTheme.colors()).error
            isRunning -> (+MaterialTheme.colors()).secondaryVariant
            else -> (+MaterialTheme.colors()).primaryVariant
        }
    ) {
        Ripple(bounded = true) {
            FlexRow(modifier = Spacing(16.dp)) {
                inflexible {
                    Column(
                        modifier = Spacing(right = 16.dp) wraps Width(40.dp)
                    ) {
                        Text(
                            text = "$startText\n—\n$endText",
                            style = (+MaterialTheme.typography()).subtitle2,
                            paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center),
                            modifier = ExpandedWidth
                        )
                    }
                }
                expanded(1f) {
                    Column {
                        Text(text = item.title, style = (+MaterialTheme.typography()).h6)
                        Text(
                            text = item.caster,
                            style = (+MaterialTheme.typography()).subtitle1
                        )
                    }
                }

                inflexible {
                    if (isRunning) {
                        WidthSpacer(width = 16.dp)
                        Column(
                            modifier = Expanded,
                            arrangement = Arrangement.Center
                        ) {
                            Button(
                                text = +stringResource(R.string.schedule_watch),
                                style = ContainedButtonStyle(
                                    color = (+MaterialTheme.colors()).secondary
                                ),
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data = BONJWA_CHANNEL_URL
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}