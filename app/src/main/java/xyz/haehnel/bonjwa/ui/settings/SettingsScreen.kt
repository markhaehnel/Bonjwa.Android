package xyz.haehnel.bonjwa.ui.settings

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.layout.Column
import androidx.ui.layout.FlexColumn
import androidx.ui.material.*
import androidx.ui.res.stringResource
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.ui.Screen
import xyz.haehnel.bonjwa.ui.TopAppBarVectorButton
import xyz.haehnel.bonjwa.ui.navigateTo

@Composable
fun SettingsScreen(openDrawer: () -> Unit) {

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
            Column {
                ListItem(
                    text = { Text("Example checkbox setting") },
                    secondaryText = { Text("Toggles the example setting on and off") },
                    trailing = {
                        val checkedState = +state { true }
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it }
                        )
                    }
                )

                ListItem(
                    text = { Text("Example switch setting") },
                    secondaryText = { Text("Switches the example setting on and off") },
                    trailing = {
                        val checkedState = +state { true }
                        Switch(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                            color = (+MaterialTheme.colors()).secondary
                        )
                    }
                )
            }
        }
    }
}