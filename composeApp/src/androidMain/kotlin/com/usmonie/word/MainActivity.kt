package com.usmonie.word

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.usmonie.word.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            androidLogger()

            modules(appModule)
        }
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
            App()
        }
    }
}
