package xyz.haehnel.bonjwa

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.ui.core.setContent
import com.jakewharton.threetenabp.AndroidThreeTen
import xyz.haehnel.bonjwa.model.SETTINGS
import xyz.haehnel.bonjwa.ui.BonjwaApp
import xyz.haehnel.bonjwa.ui.BonjwaStatus
import xyz.haehnel.bonjwa.ui.themeList

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        setContent {
            BonjwaApp()
        }
    }

    override fun onResume() {
        super.onResume()
        applySettings(sharedPrefs)
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        if (prefs != null) {
            applySettings(prefs)
        }
    }

    private fun applySettings(prefs: SharedPreferences) {
        BonjwaStatus.appTheme = themeList[prefs.getInt(SETTINGS.APP_THEME, 0)]
    }
}
