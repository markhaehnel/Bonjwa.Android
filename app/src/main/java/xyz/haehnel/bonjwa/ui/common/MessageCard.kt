package xyz.haehnel.bonjwa.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.currentTextStyle
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.darkTheme
import xyz.haehnel.bonjwa.ui.lightTheme

@Composable
fun MessageCard(message: String, @DrawableRes drawable: Int? = null) {
    Column(
        modifier = Modifier.fillMaxSize() then Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (drawable != null) {
            Icon(
                asset = vectorResource(id = drawable),
                modifier = Modifier.size(192.dp, 192.dp) then
                        Modifier.gravity(Alignment.CenterHorizontally) then
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