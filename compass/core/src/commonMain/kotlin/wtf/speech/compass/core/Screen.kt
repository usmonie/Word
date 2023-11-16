package wtf.speech.compass.core

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable

abstract class Screen(private val viewModel: ViewModel) {
    open val enterTransition: EnterTransition = slideInHorizontally(
        tween(500),
//        spring(
//            Spring.DampingRatioLowBouncy,
//            Spring.StiffnessVeryLow
//        ),
        initialOffsetX = {
//            it * 2
             it / 2
        }
    )
    open val exitTransition: ExitTransition =
        slideOutHorizontally(
            tween(500),
//            spring(
//                Spring.DampingRatioLowBouncy,
//                Spring.StiffnessVeryLow
//            ),
            targetOffsetX = {
//                -it * 2
                -it / 2
            }
        )

    abstract val id: String

    @Composable
    abstract fun Content()

    internal fun onCleared() {
        viewModel.onCleared()
    }
}

