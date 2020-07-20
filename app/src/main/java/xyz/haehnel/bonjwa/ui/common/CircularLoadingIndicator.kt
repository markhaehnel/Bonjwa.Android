package xyz.haehnel.bonjwa.ui.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.layout.ColumnScope.gravity
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme

@Composable
fun CircularLoadingIndicator() {
    Box(Modifier.fillMaxWidth()) {
        CircularProgressIndicator(color = MaterialTheme.colors.secondary, modifier = Modifier.gravity(Alignment.CenterHorizontally))
    }

}