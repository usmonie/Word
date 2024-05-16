package com.usmonie.word

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.usmonie.compass.core.RouteManager
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.Friendly
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val routeManager: RouteManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val amplitude = Amplitude(
//            Configuration(
//                apiKey = appKeys.amplitudeKey,
//                context = applicationContext,
//                defaultTracking = DefaultTrackingOptions.ALL,
//            )
//        )

//        val logger = DefaultLogger(applicationContext, Firebase.analytics, amplitude)

        enableEdgeToEdge()
        setContent {
            App(routeManager, AppState(WordThemes.DEEP_INDIGO, Friendly))
        }
    }
}
