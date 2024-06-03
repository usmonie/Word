package com.usmonie.word

import android.app.Application
import com.google.firebase.FirebaseApp

class WordApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
