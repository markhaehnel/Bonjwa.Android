package xyz.haehnel.bonjwa.ui.settings

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
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

    ScrollableColumn (modifier = Modifier.padding(top = 4.dp)) {
        Card(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.fillMaxWidth() then Modifier.padding(
                8.dp
            ),
            elevation = 4.dp,
            shape = MaterialTheme.shapes.small
        ) {
            Column (modifier = Modifier.padding(16.dp, 16.dp)) {
                val appThemeOptions = listOf(
                    stringResource(R.string.settings_colorScheme_dark),
                    stringResource(R.string.settings_colorScheme_light)
                )
                val (selectedOption, onOptionSelected) = remember {
                    mutableStateOf(appThemeOptions[appViewModel.appThemeIndex.value])
                }

                Text(stringResource(R.string.settings_colorScheme), style = MaterialTheme.typography.h6)

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
                            .padding(top = 16.dp)
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
    }
}
