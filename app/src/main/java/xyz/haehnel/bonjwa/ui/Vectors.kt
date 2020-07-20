package xyz.haehnel.bonjwa.ui

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.clickable
import androidx.ui.graphics.Color
import androidx.ui.layout.padding
import androidx.ui.layout.size
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp

@Composable
fun TopAppBarVectorButton(
    @DrawableRes id: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(vectorResource(id), tint = MaterialTheme.colors.onBackground)
    }
}
