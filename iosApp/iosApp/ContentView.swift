import UIKit
import SwiftUI
import ComposeApp
import GoogleMobileAds

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {

        GADMobileAds.sharedInstance().requestConfiguration.testDeviceIdentifiers = 
            [ "108aeb6dd38eab00ae51269161789e8e" ]

        return MainViewControllerKt.MainViewController(
            analytics:  NativeAnalytics(),
            bannerAd: { SwiftView(content: Banner(bannerID: "ca-app-pub-2198867984469198/3121295852")) },
            showInterstitialAd: {},
            showRewardedLifeInterstitialAd: {_,_ in },
            showRewardedHintInterstitialAd: {_,_ in },
            showRewardedNewGameInterstitialAd: {_,_ in },
            getAdsManagerState: {
                UiAdsManagerState(
                    isInterstitialReady: false,
                    isRewardLifeReady: false,
                    isRewardHintReady: false,
                    isRewardNewGameReady: false,
                    isBannerReady: true
                )
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
            .edgesIgnoringSafeArea(.top)
            .edgesIgnoringSafeArea(.bottom)
    }
}
