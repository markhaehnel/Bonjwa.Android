package xyz.haehnel.bonjwa.ui.events

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutWidth
import androidx.ui.layout.Row
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.ui.darkTheme
import xyz.haehnel.bonjwa.ui.lightTheme

@Composable
fun EventItemCard(item: BonjwaEventItem) {
    Card(
        color = (MaterialTheme.colors()).primaryVariant
    ) {
        Row(modifier = LayoutPadding(16.dp) + LayoutWidth.Fill) {
            Column {
                Text(text = item.title, style = (MaterialTheme.typography()).h6)
                Text(
                    text = item.date,
                    style = (MaterialTheme.typography()).subtitle1
                )
            }
        }
    }
}

@Preview("Event Item Card (Dark)")
@Composable
fun previewDarkEventItemCard() {
    MaterialTheme(colors = darkTheme) {
        EventItemCard(item = BonjwaEventItem("Community Treffen", "14. November"))
    }
}

@Preview("Event Item Card (Light)")
@Composable
fun previewLightEventItemcard() {
    MaterialTheme(colors = lightTheme) {
        EventItemCard(item = BonjwaEventItem("Community Treffen", "14. November"))
    }
}