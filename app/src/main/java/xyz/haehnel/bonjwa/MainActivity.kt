package xyz.haehnel.bonjwa

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.jakewharton.threetenabp.AndroidThreeTen
import xyz.haehnel.bonjwa.ui.BonjwaApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this)

        val currentNightMode = applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        setContent {
            BonjwaApp(currentNightMode == Configuration.UI_MODE_NIGHT_YES)
        }
    }
}
