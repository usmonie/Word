package wtf.speech.core.ui.firebase.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import wtf.speech.core.ui.firebase.GoogleAuthUiProvider

interface GoogleButtonUiContainerScope {
    fun onClick()
}

@Composable
fun GoogleButtonContainer(
    googleAuthUiProvider: GoogleAuthUiProvider,
    modifier: Modifier = Modifier,
    content: @Composable GoogleButtonUiContainerScope.() -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val uiContainerScope = remember {
        object : GoogleButtonUiContainerScope {
            override fun onClick() {
                coroutineScope.launch {
                    googleAuthUiProvider.signIn({
                        println("error $it")
                    })
                }
            }
        }
    }
    Box(
        modifier = modifier,
        content = { uiContainerScope.content() }
    )
}