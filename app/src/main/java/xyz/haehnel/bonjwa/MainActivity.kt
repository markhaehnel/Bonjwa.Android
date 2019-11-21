package xyz.haehnel.bonjwa

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.ui.core.setContent
import xyz.haehnel.bonjwa.ui.BonjwaApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentNightMode = applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        setContent {
            BonjwaApp(currentNightMode == Configuration.UI_MODE_NIGHT_YES)
        }
    }
}
