package com.usmonie.word.features.main.ui

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import wtf.speech.compass.core.Screen

class MainScreen: Screen() {
    override val id: String = "MAIN_SCREEN"

    @Composable
    override fun Content() {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(false, {}, {}, label = { Text("Dashboard") })
                }
            }
        ) {

        }
    }
}