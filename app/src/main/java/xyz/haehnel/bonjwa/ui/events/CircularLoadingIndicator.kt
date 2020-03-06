package xyz.haehnel.bonjwa.ui.events

import androidx.compose.Composable
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutWidth
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme

@Composable
fun CircularLoadingIndicator() {
    Container(
        modifier = LayoutWidth.Fill
    ) {
        CircularProgressIndicator(MaterialTheme.colors().secondary)
    }
}