
import com.usmonie.word.features.onboarding.ui.FirebaseAuthenticationUtils
import platform.UIKit.UIApplication
import wtf.speech.core.ui.firebase.GoogleAuthUiProvider
import wtf.word.core.domain.firebase.models.GoogleUser

internal class GoogleAuthUiProviderImpl(
    private val onLaunchSign: (onSuccess: (GoogleUser) -> Unit, onFailure: (Throwable) -> Unit) -> Unit,
) : GoogleAuthUiProvider {
    override suspend fun signIn(onFailure: (Throwable) -> Unit) {
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController

        if (rootViewController != null) {
            onLaunchSign(FirebaseAuthenticationUtils::login, onFailure)
        }
    }
}