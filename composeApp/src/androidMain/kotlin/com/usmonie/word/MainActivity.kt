package com.usmonie.word

import App
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.data.db.DriverFactory
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val driverFactory = DriverFactory(this)

        enableEdgeToEdge()
        setContent {
            val view = LocalView.current
            val isDark = isSystemInDarkTheme()

            SideEffect {
                val window = (view.context as Activity).window
                val insets = WindowCompat.getInsetsController(window, view)
                window.statusBarColor =
                    Color.Transparent.toArgb() // choose a status bar color
                window.navigationBarColor =
                    Color.Transparent.toArgb() // choose a navigation bar color
                insets.isAppearanceLightStatusBars = isDark
                insets.isAppearanceLightNavigationBars = isDark
            }
            App(driverFactory, UserRepositoryImpl(KVault(this@MainActivity)))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(DriverFactory(LocalContext.current), UserRepositoryImpl(KVault(LocalContext.current)))
}