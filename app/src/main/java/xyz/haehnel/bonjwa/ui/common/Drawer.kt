package xyz.haehnel.bonjwa.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.Text
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButton
import androidx.ui.material.surface.Surface
import androidx.ui.unit.dp
import xyz.haehnel.bonjwa.ui.VectorImage

@Composable
fun AppDrawer(
    headerContent: @Composable (() -> Unit)? = null,
    bodyContent: @Composable() () -> Unit,
    footerContent: @Composable (() -> Unit)? = null
) {
    val dividerColor = MaterialTheme.colors().onPrimary.copy(alpha = 0.05f)

    Column(
        modifier = LayoutHeight.Fill,
        arrangement = Arrangement.Begin
    ) {
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
    val colors = MaterialTheme.colors()
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
        TextButton(onClick = action, modifier = LayoutPadding(8.dp)) {
            Row(LayoutWidth.Fill, arrangement = Arrangement.Begin) {
                VectorImage(
                    modifier = LayoutGravity.Center,
                    id = icon,
                    tint = textIconColor
                )
                Spacer(LayoutWidth(16.dp))
                Text(
                    modifier = LayoutGravity.Center,
                    text = label,
                    style = (MaterialTheme.typography()).body1.copy(
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
    val color = (MaterialTheme.colors()).onPrimary.copy(0.6f)

    Surface(
        modifier = modifier
    ) {
        TextButton(onClick = action, modifier = LayoutPadding(left = 8.dp, top = 4.dp, right = 8.dp, bottom = 4.dp)) {
            Row(LayoutWidth.Fill, arrangement = Arrangement.Begin) {
                if (icon != null) {
                    VectorImage(
                        modifier = LayoutGravity.Center,
                        id = icon,
                        tint = color
                    )
                    Spacer(LayoutWidth(16.dp))
                }
                Text(
                    modifier = LayoutGravity.Center,
                    text = text,
                    style = (MaterialTheme.typography()).subtitle2.copy(
                        color = color
                    )
                )
            }
        }
    }
}
