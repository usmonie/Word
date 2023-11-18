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
        let banner = GADBannerView(adSize: GADAdSizeFullBanner)
        let controller = ViewController(bannerView: banner)
        banner.rootViewController = controller
        return MainViewControllerKt.MainViewController(adMob: UiAdMob(bannerUiView: { () -> UIView in
            let textField = UITextField(frame: CGRectMake(0.0, 0.0, 0.0, 0.0))
            
            return textField
        }),nativeAnalytics: NativeAnalytics())
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

