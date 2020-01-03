package xyz.haehnel.bonjwa.ui.common

import androidx.annotation.DrawableRes

data class ActionBarItem(@DrawableRes val vectorResource: Int, val action: () -> Unit)
