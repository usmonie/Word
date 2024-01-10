package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * A composable that observes changes to the current screen in [RouteManagerImpl] and displays its content.
 *
 * @param routeManager The [RouteManagerImpl] responsible for managing navigation.
 */
@ExperimentalAnimationApi
@Composable
fun NavigationHost(
    routeManager: RouteManager,
    modifier: Modifier = Modifier,
    isGestureNavigationEnabled: Boolean = false
) {
    CompositionLocalProvider(LocalRouteManager provides routeManager) {
        val state by routeManager.state.collectAsState()
        val gestureState by routeManager.gestureState.collectAsState(null)
        val offset = remember { Animatable(0f) }
        val coroutineScope = rememberCoroutineScope()
        val gesture = gestureState
        val currentState = state

        LaunchedEffect(gestureState) {
            if (gesture is BackGesture.Ended) {
                offset.animateTo(gesture.screenWidth.toFloat())
            }
        }

        BackGestureHandler(
            offset,
            { coroutineScope.launch { offset.snapTo(it) } },
            routeManager,
            isGestureNavigationEnabled
        ) {
            if (currentState is BackGesture) {
                BackGestureDraggingContent(currentState, offset)
            } else {
                AnimationScreen(state, modifier)
            }
        }
    }
}

@Composable
private fun BackGestureDraggingContent(
    currentState: BackGesture,
    offset: Animatable<Float, AnimationVector1D>
) {
    Box(Modifier.fillMaxSize()) {
        currentState.previousScreen.Content()
    }

    Box(
        Modifier
            .offset { IntOffset(offset.value.toInt(), 0) }
            .shadow(4.dp, clip = false)
            .fillMaxSize()) {
        currentState.currentScreen.Content()
    }
}

@Composable
private fun AnimationScreen(
    state: NavigationState,
    modifier: Modifier
) {
    val zIndices = remember { mutableStateMapOf<String, Float>() }
    val transition = updateTransition(state.targetScreen, label = "entry")
    transition.AnimatedContent(
        modifier,
        transitionSpec = {
            // If the initialState of the AnimatedContent is not in visibleEntries, we are in
            // a case where visible has cleared the old state for some reason, so instead of
            // attempting to animate away from the initialState, we skip the animation.
            val initialZIndex = zIndices[initialState.id]
                ?: 0f.also { zIndices[initialState.id] = 0f }
            val targetZIndex = when {
                targetState.id == initialState.id -> initialZIndex
                else -> initialZIndex + 1f
            }.also { zIndices[targetState.id] = it }

            ContentTransform(
                when (state) {
                    is NavigationState.Back -> targetState.popEnterTransition(this)
                    is NavigationState.Next -> targetState.enterTransition(this)
                    else -> fadeIn(initialAlpha = 1f)
                },
                when (state) {
                    is NavigationState.Back -> initialState.popExitTransition(this)
                    is NavigationState.Next -> initialState.exitTransition(this)
                    else -> fadeOut(targetAlpha = 1f)
                },
                targetZIndex,
                null
            )
        },
        Alignment.Center,
        contentKey = { it.id },
        content = { screen ->
            screen.Content()
        }
    )

    LaunchedEffect(transition.currentState, transition.targetState) {
        if (transition.currentState == transition.targetState) {
            zIndices
                .filter { it.key != transition.targetState.id }
                .forEach { zIndices.remove(it.key) }
        }
    }
}


class Ref(var value: Int)

// Note the inline function below which ensures that this function is essentially
// copied at the call site to ensure that its logging only recompositions from the
// original call site.
@Composable
inline fun LogCompositions(tag: String, msg: String) {
    val ref = remember { Ref(0) }
    SideEffect { ref.value++ }
    println("$tag Compositions: $msg ${ref.value}")
}