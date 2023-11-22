//
//  ContentView.swift
//  iosApp
//
//  Created by Usman Akhmedov on 11/13/23.
//

import SwiftUI
import ComposeApp
import design
import GoogleMobileAds

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(adMob: UiAdMob(bannerUiView: {
            SwiftUIInUIView(
                content: Banner(bannerID: "")
            )

        }), nativeAnalytics: NativeAnalytics())
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                .edgesIgnoringSafeArea(.top)
                .edgesIgnoringSafeArea(.bottom)
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
