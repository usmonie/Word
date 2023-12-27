//
//  AdBannerView.swift
//  iosApp
//
//  Created by Usman Akhmedov on 11/18/23.
//

import UIKit
import GoogleMobileAds
import SwiftUI

private struct BannerVC: UIViewControllerRepresentable {
    var bannerID: String

    @Binding var viewWidth: CGFloat
    func makeUIViewController(context: Context) -> UIViewController {
        
        let view = GADBannerView(adSize: GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(viewWidth))

        let viewController = UIViewController()
        view.adUnitID = bannerID
        view.rootViewController = viewController
        viewController.view.addSubview(view)
        view.load(GADRequest())

        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Update the ad size and load a new ad request when the view width changes
        uiViewController.view.subviews.first?.frame.size = GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(viewWidth).size
        (uiViewController.view.subviews.first as? GADBannerView)?.load(GADRequest())
    }
}

struct Banner: View {
    var bannerID: String
    @State var viewWidth: CGFloat = 0 // Add a state variable for the view width

    var size: CGSize {
        return GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(viewWidth).size // Use the adaptive banner size function
    }


    var body: some View {
        BannerVC(bannerID: bannerID, viewWidth: $viewWidth)
            .frame(width: size.width, height: size.height)
            .onAppear {
                // Get the initial view width
                viewWidth = UIScreen.main.bounds.width
            }
            .onReceive(NotificationCenter.default.publisher(for: UIDevice.orientationDidChangeNotification)) { _ in
                // Update the view width when the device orientation changes
                viewWidth = UIScreen.main.bounds.width
            }
    }
}

class ViewController: UIViewController {


    var bannerView: GADBannerView


    init(bannerView: GADBannerView) {
        self.bannerView = bannerView
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }


    override func viewDidLoad() {
        super.viewDidLoad()
        print("VIEWCONTROLLER STARTED")
        // In this case, we instantiate the banner with desired ad size.
//        bannerView = GADBannerView(adSize: GADAdSizeBanner)

        addBannerViewToView(bannerView)

        bannerView.adUnitID = "ca-app-pub-3940256099942544/2934735716"
        bannerView.load(GADRequest())
    }

    func addBannerViewToView(_ bannerView: GADBannerView) {

        bannerView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(bannerView)
        view.addConstraints(
            [NSLayoutConstraint(item: bannerView,
                attribute: .bottom,
                relatedBy: .equal,
                toItem: view.safeAreaLayoutGuide,
                attribute: .bottom,
                multiplier: 1,
                constant: 0),
                NSLayoutConstraint(item: bannerView,
                    attribute: .centerX,
                    relatedBy: .equal,
                    toItem: view,
                    attribute: .centerX,
                    multiplier: 1,
                    constant: 0)
            ])
    }
}
