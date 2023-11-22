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

    func makeUIViewController(context: Context) -> UIViewController {
        let view = GADBannerView(adSize: GADAdSizeLargeBanner)

        let viewController = UIViewController()
//        #if DEBUG
//        view.adUnitID = "ca-app-pub-3940256099942544/6300978111"
//        #else
        view.adUnitID = bannerID
//        #endif
        view.rootViewController = viewController
        viewController.view.addSubview(view)
        view.load(GADRequest())

        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct Banner: View {
    var bannerID: String


    var size: CGSize {
        return GADAdSizeLargeBanner.size
    }

    var body: some View {
        BannerVC(bannerID: bannerID)
                .frame(width: size.width, height: size.height)
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
