package xyz.haehnel.bonjwa.ui.events

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.onActive
import androidx.compose.remember
import androidx.ui.core.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Card
import androidx.ui.res.stringResource
import androidx.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
import xyz.haehnel.bonjwa.ui.BonjwaAppDrawer
import xyz.haehnel.bonjwa.ui.Screen
import xyz.haehnel.bonjwa.ui.TopAppBarVectorButton
import xyz.haehnel.bonjwa.ui.common.ActionBarItem

@Model
class EventsModel(
    var isLoading: Boolean = false,
    var events: MutableList<BonjwaEventItem> = mutableListOf(),
    var error: String? = null
) {

    fun fetchEvents() {
        error = null
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrievedEvents = ScheduleRepository().getEvents().await()
                withContext(Dispatchers.Main) {
                    events.clear()
                    events.addAll(retrievedEvents)
                    isLoading = false
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    error = "Fehler beim Laden der Events."
                    isLoading = false
                }
            }
        }
    }
}

@Composable
fun EventsScreen(scaffoldState: ScaffoldState = remember { ScaffoldState() }) {
    val model = remember { EventsModel() }

    val actionData = listOf(
        ActionBarItem(R.drawable.ic_refresh) { model.fetchEvents() }
    )

    onActive {
        model.fetchEvents()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            BonjwaAppDrawer(
                currentScreen = Screen.Events,
                closeDrawer = { scaffoldState.drawerState = DrawerState.Closed }
            )
        },
        topAppBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.events)) },
                navigationIcon = {
                    TopAppBarVectorButton(id = R.drawable.ic_hamburger, onClick = {
                        scaffoldState.drawerState = DrawerState.Opened
                    })
                },
                actions = {
                    actionData.forEach {
                        TopAppBarVectorButton(
                            id = it.vectorResource,
                            onClick = { it.action() }
                        )
                    }
                }
            )
        },
        bodyContent = {
            if (model.isLoading) {
                Row(
                    modifier = LayoutSize.Fill,
                    arrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator((MaterialTheme.colors()).secondary)
                }
            } else if (model.error != null) {
                Column(
                    modifier = LayoutSize.Fill,
                    arrangement = Arrangement.Center
                ) {
                    Card(
                        elevation = 4.dp,
                        color = (MaterialTheme.colors()).error
                    ) {
                        Column(
                            arrangement = Arrangement.Center,
                            modifier = LayoutPadding(16.dp)
                        ) {
                            Text(text = model.error!!)
                            Spacer(LayoutHeight(height = 8.dp))
                            Button(
                                onClick = { model.fetchEvents() }
                            ) {
                                Text(stringResource(R.string.retry))
                            }
                        }
                    }
                }
            } else {
                if (!model.events.isNullOrEmpty()) {
                    VerticalScroller {
                        Column(
                            modifier = LayoutWidth.Fill
                        ) {
                            for (item in model.events) {
                                EventItemCard(item = item)
                                Divider()
                            }
                        }
                    }

                } else {
                    Column(
                    ) {
                        Card(
                            color = (MaterialTheme.colors()).primaryVariant
                        ) {
                            Text(
                                stringResource(R.string.events_empty),
                                modifier = LayoutPadding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun EventItemCard(item: BonjwaEventItem) {
    Card(
        color = (MaterialTheme.colors()).primaryVariant
    ) {
        Row(modifier = LayoutPadding(16.dp) + LayoutWidth.Fill) {
            Column {
                Text(text = item.title, style = (MaterialTheme.typography()).h6)
                Text(
                    text = item.date,
                    style = (MaterialTheme.typography()).subtitle1
                )
            }
        }
    }
}