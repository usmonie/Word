package com.usmonie.word.features.learning.ui.learn

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.usmonie.word.features.learning.ui.learn.LearnScreen.Builder.Companion.ID
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder

class LearnScreen : Screen() {

    override val id: String = ID

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val pagerState = rememberPagerState { 4 }
        Scaffold() {
            HorizontalPager(pagerState) {

            }
        }
    }

    class Builder: ScreenBuilder {

        companion object {
            const val ID = "LEARN_SCREEN"
        }

        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return LearnScreen()
        }
    }
}

