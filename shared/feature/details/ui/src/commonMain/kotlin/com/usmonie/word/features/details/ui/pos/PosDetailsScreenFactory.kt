package com.usmonie.word.features.details.ui.pos

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.dictionary.ui.models.WordUi

class PosDetailsScreenFactory : ScreenFactory {
    override val id: ScreenId = ID
    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        require(extra is ScreenExtra)

        return PosDetailsScreen(PosDetailsViewModel(extra.word))
    }

    companion object {
        val ID = ScreenId("PosDetailedScreen")
    }

    data class ScreenExtra(val word: WordUi) : Extra
}
