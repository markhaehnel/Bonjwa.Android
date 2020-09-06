package xyz.haehnel.bonjwa.ui.common

import androidx.compose.ui.graphics.vector.VectorAsset

data class ActionBarItem(val icon: VectorAsset, val action: () -> Unit)
