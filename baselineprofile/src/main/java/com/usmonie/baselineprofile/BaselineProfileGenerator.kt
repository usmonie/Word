package com.usmonie.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the Generate Baseline Profile run configuration,
 * or directly with `generateBaselineProfile` Gradle task:
 * ```
 * ./gradlew :composeApp:generateReleaseBaselineProfile -Pandroid.testInstrumentationRunnerArguments.androidx.benchmark.enabledRules=BaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [StartupBenchmarks] benchmark.
 *
 * When using this class to generate a baseline profile, only API 33+ or rooted API 26+ are supported.
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        rule.collect("com.usmonie.word") {
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.

            // Start default activity for your app
            pressHome()
            startActivityAndWait()

            device.wait(Until.hasObject(By.pkg("com.usmonie.word").depth(0)), 15_000)
            searchWord()
            openDetails("random_word_card")
            openFavorites()
            openGames()
            openSettings()

            // Example: Tap on a specific item, such as a word card
//            val wordCard = device.findObject(By.desc("Word Card Description")) // Replace with actual content-desc
//            wordCard?.click()

            // TODO Write more interactions to optimize advanced journeys of your app.
            // For example:
            // 1. Wait until the content is asynchronously loaded
            // 2. Scroll the feed content
            // 3. Navigate to detail screen

            // Check UiAutomator documentation for more information how to interact with the app.
            // https://d.android.com/training/testing/other-components/ui-automator
        }
    }

    private fun MacrobenchmarkScope.openSettings() {
        val settings = device.findObject(By.text("[S]ettings"))
        settings.click()
        device.waitForIdle(400L)
        val themeBritishRacingGreen = device.findObject(By.text("British Racing Green"))
        val themeRichMaroon = device.findObject(By.text("Rich Maroon"))
        val themeDeepIndigo = device.findObject(By.text("Deep Indigo"))
        themeBritishRacingGreen.click()
        device.waitForIdle(200L)
        themeRichMaroon.click()
        device.waitForIdle(200L)
        themeDeepIndigo.click()
        device.waitForIdle(200L)
        device.pressBack()
    }

    private fun MacrobenchmarkScope.openFavorites() {
        val favorites = device.findObject(By.text("[F]avorites"))
        favorites.click()
        device.waitForIdle(200L)
        device.pressBack()
    }

    private fun MacrobenchmarkScope.openGames() {
        val games = device.findObject(By.text("[G]ames"))
        games.click()
        device.waitForIdle(200L)
        val hangman = device.findObject(By.text("[H]angman"))
        hangman.click()
        device.waitForIdle(200L)

        device.pressBack()
    }

    private fun MacrobenchmarkScope.searchWord() {
        val searchBar = device.findObject(By.desc("search_bar"))
        searchBar.click()
        searchBar.text = "w"
        device.wait(Until.hasObject(By.desc("search_card")), 500L)
        searchBar.text = "wo"
        device.wait(Until.hasObject(By.desc("search_card")), 500L)

        searchBar.text = "wor"
        device.wait(Until.hasObject(By.desc("search_card")), 500L)

        searchBar.text = "word"
        device.wait(Until.hasObject(By.desc("search_card")), 1500L)
        openDetails("search_card")
        searchBar.fling(Direction.LEFT)
    }

    private fun MacrobenchmarkScope.openDetails(wordCardTag: String) {
        val wordCard = device.findObject(By.desc(wordCardTag))
        wordCard.click()
        device.waitForIdle(500L)

        val details = device.findObject(By.desc("BASE_LAZY_COLUMN"))
        details.setGestureMargin(device.displayWidth / 5)
        details.fling(Direction.DOWN)
        details.fling(Direction.DOWN)
        details.fling(Direction.UP)
        details.fling(Direction.UP)
        details.fling(Direction.DOWN)
        details.fling(Direction.DOWN)
        details.fling(Direction.DOWN)
        details.fling(Direction.DOWN)
        details.fling(Direction.UP)
        details.fling(Direction.UP)
        details.fling(Direction.UP)
        details.fling(Direction.UP)
        device.waitForIdle()

        device.pressBack()
    }
}