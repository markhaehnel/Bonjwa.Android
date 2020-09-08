package xyz.haehnel.bonjwa.ui

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import xyz.haehnel.bonjwa.model.SETTINGS
import xyz.haehnel.bonjwa.ui.common.ActionBarItem

class BonjwaAppViewModel : ViewModel() {
    private lateinit var _prefs: SharedPreferences

    fun initialize(
        sharedPrefs: SharedPreferences
    ) {
        _prefs = sharedPrefs
        setAppTheme(_prefs.getInt(SETTINGS.APP_THEME, 0))
    }

    var appThemeIndex = mutableStateOf(0)
        private set
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Schedule)
        private set
    var topBarTitle = mutableStateOf("")
        private set
    var topBarActions = mutableStateListOf<ActionBarItem>()
        private set
    var fabAction: MutableState<ActionBarItem?> = mutableStateOf(null)
        private set

    fun setAppTheme(newAppThemeIndex: Int) {
        appThemeIndex.value = newAppThemeIndex
        _prefs.edit().putInt(SETTINGS.APP_THEME, newAppThemeIndex).apply()
    }

    fun setCurrentScreen(screen: Screen) {
        currentScreen.value = screen
    }

    fun setTopBar(
        title: String,
        actions: Collection<ActionBarItem>? = null,
        newFabAction: ActionBarItem? = null
    ) {
        topBarTitle.value = title
        topBarActions.clear()
        if (actions != null) topBarActions.addAll(actions)
        fabAction.value = newFabAction
    }
}

enum class ScreenName { SCHEDULE, EVENTS, SETTINGS }

sealed class Screen(val id: ScreenName) {
    object Schedule : Screen(ScreenName.SCHEDULE)
    object Events : Screen(ScreenName.EVENTS)
    object Settings : Screen(ScreenName.SETTINGS)
}
