package xyz.haehnel.bonjwa.ui

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.core.Modifier
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Surface
import androidx.ui.res.stringResource
import xyz.haehnel.bonjwa.BuildConfig
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.home.HomeScreen
import xyz.haehnel.bonjwa.ui.settings.SettingsScreen

@Composable
fun BonjwaApp() {
    MaterialTheme(colors = appTheme) {
        val (drawerState, onDrawerStateChange) = +state { DrawerState.Closed }
        ModalDrawerLayout(
            drawerState = drawerState,
            onStateChange = onDrawerStateChange,
            drawerContent = {
                AppDrawer(
                    currentScreen = BonjwaStatus.currentScreen,
                    closeDrawer = { onDrawerStateChange(DrawerState.Closed) }
                )
            },
            bodyContent = { AppContent { onDrawerStateChange(DrawerState.Opened) } }
        )
    }
}

@Composable
private fun AppContent(openDrawer: () -> Unit) {
    Crossfade(BonjwaStatus.currentScreen) { screen ->
        Surface(color = (+MaterialTheme.colors()).background) {
            when (screen) {
                is Screen.Home -> HomeScreen { openDrawer() }
                is Screen.Settings -> SettingsScreen { openDrawer() }
            }
        }
    }
}

@Composable
private fun AppDrawer(
    currentScreen: Screen,
    closeDrawer: () -> Unit
) {
    Column(modifier = Expanded, arrangement = Arrangement.Begin) {
        HeightSpacer(24.dp)
        Row(modifier = Spacing(16.dp)) {
            VectorImage(id = R.drawable.ic_logo, tint = (+MaterialTheme.colors()).onBackground)
            WidthSpacer(16.dp)
            Column {
                Text(
                    "${+stringResource(R.string.app_name)} ${+stringResource(R.string.schedule)}",
                    style = (+MaterialTheme.typography()).h6
                )
                Text(
                    "Die inoffizielle Community App",
                    style = (+MaterialTheme.typography()).subtitle1.copy(
                        color = (+MaterialTheme.colors()).onPrimary.copy(alpha = 0.6f)
                    )

                )
            }
        }
        Divider(color = Color(0x14CCCCCC))
        DrawerButton(
            icon = R.drawable.ic_calendar,
            label = +stringResource(R.string.schedule),
            isSelected = currentScreen == Screen.Home
        ) {
            navigateTo(Screen.Home)
            closeDrawer()
        }
        DrawerButton(
            icon = R.drawable.ic_settings,
            label = +stringResource(R.string.settings),
            isSelected = currentScreen == Screen.Settings
        ) {
            navigateTo(Screen.Settings)
            closeDrawer()
        }
        Divider(color = Color(0x14CCCCCC))
        DrawerInfo(
                text =
                    """
                    ${BuildConfig.APPLICATION_ID}
                    ${BuildConfig.VERSION_NAME} (${BuildConfig.BUILD_TYPE})
                    """.trimIndent(),
                icon = R.drawable.ic_info)
    }
}

@Composable
private fun DrawerButton(
    modifier: Modifier = Modifier.None,
    @DrawableRes icon: Int,
    label: String,
    isSelected: Boolean,
    action: () -> Unit
) {
    val colors = +MaterialTheme.colors()
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
        modifier = modifier ,
        color = backgroundColor
    ) {
        Button(onClick = action, style = TextButtonStyle(), modifier = Spacing(left = 8.dp, top = 8.dp, right = 8.dp, bottom = 8.dp)) {
            Row (ExpandedWidth, arrangement = Arrangement.Begin) {
                VectorImage(
                    modifier = Gravity.Center,
                    id = icon,
                    tint = textIconColor
                )
                WidthSpacer(16.dp)
                Text(
                    modifier = Gravity.Center,
                    text = label,
                    style = (+MaterialTheme.typography()).body1.copy(
                        color = textIconColor
                    )
                )
            }
        }
    }
}

@Composable
private fun DrawerInfo(
    modifier: Modifier = Modifier.None,
    @DrawableRes icon: Int? = null,
    text: String,
    action: (() -> Unit)? = null
) {
    val color = (+MaterialTheme.colors()).onPrimary.copy(0.6f)

    Surface(
        modifier = modifier
    ) {
        Button(onClick = action, style = TextButtonStyle(), modifier = Spacing(left = 8.dp, top = 4.dp, right = 8.dp, bottom = 4.dp)) {
            Row (ExpandedWidth, arrangement = Arrangement.Begin) {
                if (icon != null) {
                    VectorImage(
                        modifier = Gravity.Center,
                        id = icon,
                        tint = color
                    )
                    WidthSpacer(16.dp)
                }
                Text(
                    modifier = Gravity.Center,
                    text = text,
                    style = (+MaterialTheme.typography()).subtitle2.copy(
                        color = color
                    )
                )
            }
        }
    }
}