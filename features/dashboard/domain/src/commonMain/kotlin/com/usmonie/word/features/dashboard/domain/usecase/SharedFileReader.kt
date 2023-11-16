package com.usmonie.word.features.dashboard.domain.usecase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class SharedFileReader() {
    fun loadJsonFile(fileName: String): String?
}