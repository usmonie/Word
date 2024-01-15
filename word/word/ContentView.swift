//
//  ContentView.swift
//  iosApp
//
//  Created by Usman Akhmedov on 11/13/23.
//

import SwiftUI
import ComposeApp
import GoogleMobileAds
import AmplitudeSwift
struct ComposeView: UIViewControllerRepresentable {
    let onShowAd: () -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        var controller: UIViewController
        let amplitude: Amplitude
        #if DEBUG
             amplitude = Amplitude(configuration: Configuration(
                apiKey: "244f468ae1266bd4dbfb8fd739cdc87e"
            ))
        #else
            amplitude = Amplitude(configuration: Configuration(
                apiKey: "78f6281a02303905977f69718014b64f"
            ))
        #endif
        controller = MainViewControllerKt.MainViewController(
            onViewDidLoad: {},
            bannerUiView: {
                SwiftUIInUIView(content: Banner(bannerID: "ca-app-pub-2198867984469198/3121295852"))
            },
            rewardedInterstitialView: {
                onShowAd()
            },
            nativeAnalytics: NativeAnalytics(amplitude: amplitude)
        )

        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        print("updateUIViewController")
    }
}

struct ContentView: View {
    let interstitialAd = InterstitalRewardedView()
    @State private var interstitialVC: InterstitalRewardedViewController?

    var body: some View {
        ZStack {
            interstitialAd
            ComposeView(onShowAd: { interstitialVC?.rewardAdTouched() })
                    .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                    .edgesIgnoringSafeArea(.top)
                    .edgesIgnoringSafeArea(.bottom)

        }
                .onAppear {
                    interstitialVC = interstitialAd.viewController
                }
    }
}

class SwiftUIInUIView<Content: View>: UIView {
    init(content: Content) {
        super.init(frame: CGRect())
        let hostingController = UIHostingController(rootView: content)
        hostingController.view.translatesAutoresizingMaskIntoConstraints = false
        addSubview(hostingController.view)
        NSLayoutConstraint.activate([
            hostingController.view.topAnchor.constraint(equalTo: topAnchor),
            hostingController.view.leadingAnchor.constraint(equalTo: leadingAnchor),
            hostingController.view.trailingAnchor.constraint(equalTo: trailingAnchor),
            hostingController.view.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
