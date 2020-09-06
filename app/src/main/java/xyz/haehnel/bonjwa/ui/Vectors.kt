package xyz.haehnel.bonjwa.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.vectorResource

@Composable
fun TopAppBarVectorButton(
    @DrawableRes id: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(vectorResource(id), tint = MaterialTheme.colors.onBackground)
    }
}
