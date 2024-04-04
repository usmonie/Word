package com.usmonie.word.features.learning.ui.models

import androidx.compose.runtime.Immutable
import com.usmonie.word.features.dashboard.ui.models.ExampleUi
import com.usmonie.word.features.dashboard.ui.models.SenseCombinedUi
import com.usmonie.word.features.dashboard.ui.models.SoundUi
import com.usmonie.word.features.dashboard.ui.models.TranslationUi
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi

data class Lesson(
    val id: String,
    val exercise: List<Exercise>
)

sealed class Exercise {

    @Immutable
    data class NewWord(
        val id: String,
        val wordCombined: WordCombinedUi,
        val pronunciation: SoundUi,
        val sense: SenseCombinedUi,
        val example: List<ExampleUi>,
        val translation: TranslationUi
    )

    @Immutable
    data class CompleteSentence(
        val id: String,
        val wordCombined: WordCombinedUi,
        val sentence: String,
        val hiddenWord: String,
    ) : Exercise()

    @Immutable
    data class TranslateSentence(
        val id: String,
        val sentence: String,
        val answer: String,
    ) : Exercise()

    @Immutable
    data class Translate(
        val id: String,
        val wordCombined: WordCombinedUi,
        val translation: TranslationUi
    ) : Exercise()

    @Immutable
    data class TranslateTie(
        val id: String,
        val words: Map<WordCombinedUi, TranslationUi>,
    ) : Exercise()


    @Immutable
    data class Text(
        val id: String,
        val text: String,
    ) : Exercise()

    @Immutable
    data class TextQuestion(
        val id: String,
        val question: String,
        val answers: Map<String, Boolean>
    )
}
