package wtf.speech.core.ui.firebase

interface GoogleAuthUiProvider {

    /**
     * Opens Sign In with Google UI,
     */
    suspend fun signIn(onFailure: (Throwable) -> Unit)
}