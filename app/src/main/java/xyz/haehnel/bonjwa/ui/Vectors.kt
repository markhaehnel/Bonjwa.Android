package xyz.haehnel.bonjwa.ui

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutSize
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp

@Composable
fun TopAppBarVectorButton(
    @DrawableRes id: Int,
    onClick: () -> Unit,
    modifier: Modifier = LayoutPadding(start = 8.dp, end = 8.dp)
) {
    Ripple(bounded = false) {
        Clickable(onClick = onClick) {
            Container(modifier) {
                VectorImage(id = id, tint = MaterialTheme.colors().onPrimary)
            }
        }
    }
}

@Composable
fun VectorImage(
    modifier: Modifier = Modifier.None,
    @DrawableRes id: Int,
    tint: Color = Color.Transparent
) {
    val vector = vectorResource(id)
    with(DensityAmbient.current) {
        Container(
            modifier = modifier+ LayoutSize(vector.defaultWidth, vector.defaultHeight)) {
            DrawVector(vector, tint)
        }
    }
}
