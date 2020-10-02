package xyz.haehnel.bonjwa

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import xyz.haehnel.bonjwa.ui.BonjwaApp
import xyz.haehnel.bonjwa.ui.BonjwaAppViewModel


class MainActivity : AppCompatActivity() {
    private val appViewModel by viewModels<BonjwaAppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this)

        appViewModel.initialize(PreferenceManager.getDefaultSharedPreferences(applicationContext))

        setContent {
            BonjwaApp(appViewModel)
        }
    }
}

