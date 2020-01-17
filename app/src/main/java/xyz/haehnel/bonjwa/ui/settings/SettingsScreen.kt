package xyz.haehnel.bonjwa.ui.settings

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.preference.PreferenceManager
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.FlexColumn
import androidx.ui.material.ListItem
import androidx.ui.material.RadioGroup
import androidx.ui.material.TopAppBar
import androidx.ui.res.stringResource
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.SETTINGS
import xyz.haehnel.bonjwa.ui.Screen
import xyz.haehnel.bonjwa.ui.TopAppBarVectorButton
import xyz.haehnel.bonjwa.ui.navigateTo

@Composable
fun SettingsScreen() {

    val prefs = PreferenceManager.getDefaultSharedPreferences(+ambient(ContextAmbient))

    FlexColumn {
        inflexible {
            TopAppBar(
                title = {
                    Text(+stringResource(R.string.settings))
                },
                navigationIcon = {
                    TopAppBarVectorButton(
                        id = R.drawable.ic_arrow_back,
                        onClick = { navigateTo(Screen.Schedule)
                        }
                    )
                }
            )
        }
        flexible(1f) {
            VerticalScroller {
                Column {
                    ListItem(+stringResource(R.string.settings_colorScheme))
                    val appThemeOptions = listOf(
                        +stringResource(R.string.settings_colorScheme_dark),
                        +stringResource(R.string.settings_colorScheme_light)
                    )
                    val (selectedOption, onOptionSelected) = +state {
                        appThemeOptions[prefs.getInt(SETTINGS.APP_THEME, 0)]
                    }

                    RadioGroup(
                        options = appThemeOptions,
                        selectedOption = selectedOption,
                        onSelectedChange = {
                            onOptionSelected(it)
                            prefs.edit()
                                .putInt(SETTINGS.APP_THEME, appThemeOptions.indexOf(it))
                                .apply()
                        }
                    )
                }
            }
        }
    }
}
