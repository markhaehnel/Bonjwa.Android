package xyz.haehnel.bonjwa.ui

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Modifier
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButtonStyle
import androidx.ui.material.surface.Surface

@Composable
fun AppDrawer(
    headerContent: @Composable() (() -> Unit)? = null,
    bodyContent: @Composable() () -> Unit,
    footerContent: @Composable() (() -> Unit)? = null
) {
    val dividerColor = (+MaterialTheme.colors()).onPrimary.copy(alpha = 0.05f)

    Column(
        modifier = Expanded,
        arrangement = Arrangement.Begin
    ) {
        HeightSpacer(24.dp)
        if (headerContent != null) {
            headerContent()
            Divider(color = dividerColor)
        }

        bodyContent()

        if (footerContent != null) {
            Divider(color = dividerColor)
            footerContent()
        }
    }
}

@Composable
fun DrawerButton(
    modifier: Modifier = Modifier.None,
    @DrawableRes icon: Int,
    label: String,
    isSelected: Boolean,
    action: () -> Unit
) {
    val colors = +MaterialTheme.colors()
    val textIconColor = if (isSelected) {
        colors.onPrimary
    } else {
        colors.onPrimary.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.5f)
    } else {
        colors.surface
    }

    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        Button(onClick = action, style = TextButtonStyle(), modifier = Spacing(left = 8.dp, top = 8.dp, right = 8.dp, bottom = 8.dp)) {
            Row (ExpandedWidth, arrangement = Arrangement.Begin) {
                VectorImage(
                    modifier = Gravity.Center,
                    id = icon,
                    tint = textIconColor
                )
                WidthSpacer(16.dp)
                Text(
                    modifier = Gravity.Center,
                    text = label,
                    style = (+MaterialTheme.typography()).body1.copy(
                        color = textIconColor
                    )
                )
            }
        }
    }
}

@Composable
fun DrawerInfo(
    modifier: Modifier = Modifier.None,
    @DrawableRes icon: Int? = null,
    text: String,
    action: (() -> Unit)? = null
) {
    val color = (+MaterialTheme.colors()).onPrimary.copy(0.6f)

    Surface(
        modifier = modifier
    ) {
        Button(onClick = action, style = TextButtonStyle(), modifier = Spacing(left = 8.dp, top = 4.dp, right = 8.dp, bottom = 4.dp)) {
            Row (ExpandedWidth, arrangement = Arrangement.Begin) {
                if (icon != null) {
                    VectorImage(
                        modifier = Gravity.Center,
                        id = icon,
                        tint = color
                    )
                    WidthSpacer(16.dp)
                }
                Text(
                    modifier = Gravity.Center,
                    text = text,
                    style = (+MaterialTheme.typography()).subtitle2.copy(
                        color = color
                    )
                )
            }
        }
    }
}
