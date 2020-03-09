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
    Column(
        modifier = LayoutHeight.Fill,
        arrangement = Arrangement.Top
    ) {
        if (headerContent != null) {
            headerContent()
            DrawerDivider()
        }

        bodyContent()

        if (footerContent != null) {
            DrawerDivider()
            footerContent()
        }
    }
}

@Composable
fun DrawerDivider() {
    val dividerColor = MaterialTheme.colors().onPrimary.copy(alpha = 0.05f)
    Divider(color = dividerColor)
}

@Composable
fun DrawerButton(
    modifier: Modifier = Modifier.None,
    @DrawableRes icon: Int? = null,
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
            Row(LayoutWidth.Fill, arrangement = Arrangement.Start) {
                if (icon != null) {
                    VectorImage(
                        modifier = LayoutGravity.Center,
                        id = icon,
                        tint = textIconColor
                    )
                    Spacer(LayoutWidth(16.dp))
                }
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
        TextButton(onClick = action, modifier = LayoutPadding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)) {
            Row(LayoutWidth.Fill, arrangement = Arrangement.Start) {
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
