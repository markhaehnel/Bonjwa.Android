package xyz.haehnel.bonjwa.ui.events

import androidx.compose.*
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Card
import androidx.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.haehnel.bonjwa.R
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.repo.ScheduleRepository
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
fun EventsScreen(openDrawer: () -> Unit) {
    val selectedTabIndex = +state { 0 }
    val model = +memo { EventsModel() }

    val actionData = listOf(
        ActionBarItem(R.drawable.ic_refresh) { model.fetchEvents() }
    )

    +onActive {
        model.fetchEvents()
    }

    FlexColumn {
        inflexible {
            TopAppBar(
                title = { Text(+stringResource(R.string.events)) },
                actionData = actionData,
                navigationIcon = {
                    TopAppBarVectorButton(id = R.drawable.ic_hamburger, onClick = openDrawer)
                }
            ) { actionItem ->
                TopAppBarVectorButton(
                    id = actionItem.vectorResource,
                    onClick = { actionItem.action() }
                )
            }
        }
        expanded(1f) {

            if (model.isLoading) {
                Row(
                    modifier = ExpandedWidth,
                    arrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator((+MaterialTheme.colors()).secondary)
                }
            } else if (model.error != null) {
                Column(
                    modifier = ExpandedWidth,
                    arrangement = Arrangement.Center
                ) {
                    Card(
                        elevation = 4.dp,
                        color = (+MaterialTheme.colors()).error
                    ) {
                        Padding(16.dp) {
                            Column(
                                arrangement = Arrangement.Center
                            ) {
                                Text(text = model.error!!)
                                HeightSpacer(height = 8.dp)
                                Button(
                                    text = +stringResource(R.string.retry),
                                    onClick = { model.fetchEvents() }
                                )
                            }
                        }
                    }
                }
            } else {
                if (!model.events.isNullOrEmpty()) {
                    VerticalScroller {
                        Column(
                            modifier = ExpandedWidth
                        ) {
                            for (item in model.events) {
                                EventItemCard(item = item)
                                Divider()
                            }
                        }
                    }

                } else {
                    Column(
                        modifier = ExpandedWidth
                    ) {
                        Card(
                            color = (+MaterialTheme.colors()).primaryVariant,
                            modifier = ExpandedWidth
                        ) {
                            Padding(16.dp) {
                                Text(+stringResource(R.string.events_empty))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventItemCard(item: BonjwaEventItem) {
    Card(
        color = (+MaterialTheme.colors()).primaryVariant
    ) {
        Ripple(bounded = true) {
            FlexRow(modifier = Spacing(16.dp)) {
                expanded(1f) {
                    Column {
                        Text(text = item.title, style = (+MaterialTheme.typography()).h6)
                        Text(
                            text = item.date,
                            style = (+MaterialTheme.typography()).subtitle1
                        )
                    }
                }
            }
        }
    }
}