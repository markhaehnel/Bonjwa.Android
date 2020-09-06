package xyz.haehnel.bonjwa.ui.settings

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ListItem
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.onActive
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.BonjwaAppViewModel

@Composable
fun SettingsScreen(
    appViewModel: BonjwaAppViewModel
) {

    val screenTitle = "${stringResource(R.string.settings)}"

    onActive {
        appViewModel.setTopBar(screenTitle)
    }

    ScrollableColumn {
        ListItem { Text(stringResource(R.string.settings_colorScheme)) }
        val appThemeOptions = listOf(
            stringResource(R.string.settings_colorScheme_dark),
            stringResource(R.string.settings_colorScheme_light)
        )
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(appThemeOptions[appViewModel.appThemeIndex.value])
        }

        appThemeOptions.forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            appViewModel.setAppTheme(index)
                        }
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {


                RadioButton(
                    selected = text == selectedOption,
                    onClick = {
                        onOptionSelected(text)
                        appViewModel.setAppTheme(index)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
