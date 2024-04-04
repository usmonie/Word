package wtf.word.core.domain.firebase.providers

interface GoogleAuthProvider {
    suspend fun signOut()
}

