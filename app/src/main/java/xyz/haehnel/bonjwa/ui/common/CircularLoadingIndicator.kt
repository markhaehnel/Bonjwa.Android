package xyz.haehnel.bonjwa.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CircularLoadingIndicator() {
    Box(Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.secondary, modifier = Modifier.align(
                Alignment.Center
            ) then Modifier.padding(top = 8.dp)
        )
    }

}
