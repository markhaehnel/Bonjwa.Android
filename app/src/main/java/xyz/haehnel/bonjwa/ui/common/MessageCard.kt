package xyz.haehnel.bonjwa.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.currentTextStyle
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.res.stringResource
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.VectorImage
import xyz.haehnel.bonjwa.ui.darkTheme
import xyz.haehnel.bonjwa.ui.lightTheme

@Composable
fun MessageCard(message: String, @DrawableRes drawable: Int? = null) {
    Column(LayoutSize.Fill + LayoutPadding(16.dp) + LayoutAlign.Center) {
        if (drawable != null) {
            VectorImage(
                id = drawable,
                modifier = LayoutSize(192.dp, 192.dp) + LayoutGravity.Center + LayoutPadding(bottom = 16.dp)
            )
        }
        Container {
            Text(text = message, style = currentTextStyle().copy(textAlign = TextAlign.Center))
        }
    }
}

@Preview("Message Card Text (dark)")
@Composable
fun prewviewMessageCardTextDark() {
    MaterialTheme(darkTheme) {
        Surface(modifier = LayoutSize(512.dp, 128.dp)) {
            MessageCard(stringResource(id = R.string.schedule_not_published))
        }
    }
}

@Preview("Message Card Text (light)")
@Composable
fun prewviewMessageCardTextLight() {
    MaterialTheme(lightTheme) {
        Surface(modifier = LayoutSize(512.dp, 128.dp)) {
            MessageCard(stringResource(id = R.string.schedule_not_published))
        }
    }
}

@Preview("Message Card Drawable + Text (dark)")
@Composable
fun prewviewMessageCardDrawableTextDark() {
    MaterialTheme(darkTheme) {
        Surface(modifier = LayoutSize(512.dp, 512.dp)) {
            MessageCard(
                stringResource(id = R.string.schedule_not_published),
                R.drawable.ic_no_schedule_published
            )
        }
    }
}

@Preview("Message Card Drawable + Text (light)")
@Composable
fun prewviewMessageCardDrawableTextLight() {
    MaterialTheme(lightTheme) {
        Surface(modifier = LayoutSize(512.dp, 512.dp)) {
            MessageCard(
                stringResource(id = R.string.schedule_not_published),
                R.drawable.ic_no_schedule_published
            )
        }
    }
}