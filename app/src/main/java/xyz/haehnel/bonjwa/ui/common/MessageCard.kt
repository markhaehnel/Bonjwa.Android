package xyz.haehnel.bonjwa.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.currentTextStyle
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.darkTheme
import xyz.haehnel.bonjwa.ui.lightTheme

@Composable
fun MessageCard(message: String, @DrawableRes drawable: Int? = null) {
    Column(
        modifier = Modifier.fillMaxSize() + Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (drawable != null) {
            Icon(
                asset = vectorResource(id = drawable),
                modifier = Modifier.size(192.dp, 192.dp) +
                        Modifier.gravity(Alignment.CenterHorizontally) +
                        Modifier.padding(bottom = 16.dp)
            )
        }
        Surface {
            Text(text = message, style = currentTextStyle().copy(textAlign = TextAlign.Center))
        }
    }
}

@Preview("Message Card Text (dark)")
@Composable
fun previewMessageCardTextDark() {
    MaterialTheme(darkTheme) {
        Surface(modifier = Modifier.size(512.dp, 128.dp)) {
            MessageCard(stringResource(id = R.string.schedule_not_published))
        }
    }
}

@Preview("Message Card Text (light)")
@Composable
fun previewMessageCardTextLight() {
    MaterialTheme(lightTheme) {
        Surface(modifier = Modifier.size(512.dp, 128.dp)) {
            MessageCard(stringResource(id = R.string.schedule_not_published))
        }
    }
}

@Preview("Message Card Drawable + Text (dark)")
@Composable
fun previewMessageCardDrawableTextDark() {
    MaterialTheme(darkTheme) {
        Surface(modifier = Modifier.size(512.dp, 512.dp)) {
            MessageCard(
                stringResource(id = R.string.schedule_not_published),
                R.drawable.ic_no_schedule_published
            )
        }
    }
}

@Preview("Message Card Drawable + Text (light)")
@Composable
fun previewMessageCardDrawableTextLight() {
    MaterialTheme(lightTheme) {
        Surface(modifier = Modifier.size(512.dp, 512.dp)) {
            MessageCard(
                stringResource(id = R.string.schedule_not_published),
                R.drawable.ic_no_schedule_published
            )
        }
    }
}