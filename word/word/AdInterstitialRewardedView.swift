import UIKit
import SwiftUI
import GoogleMobileAds
import FirebaseAnalytics
import AppTrackingTransparency
import AdSupport

struct InterstitalRewardedView: UIViewControllerRepresentable {

    let viewController = InterstitalRewardedViewController()

    func makeUIViewController(context: UIViewControllerRepresentableContext<InterstitalRewardedView>) -> InterstitalRewardedViewController {
        return viewController
    }

    func updateUIViewController(_ uiViewController: InterstitalRewardedViewController, context: UIViewControllerRepresentableContext<InterstitalRewardedView>) {
        // update view controller here if needed
    }
}

class InterstitalRewardedViewController: UIViewController, GADFullScreenContentDelegate {
    var rewardInterstitialAd: GADRewardedInterstitialAd?
    var interstitial: GADInterstitialAd?

    override func viewDidLoad() {
        super.viewDidLoad()
        requestIDFA()
    }

    func requestIDFA() {
        print("requestIDFA")
        ATTrackingManager.requestTrackingAuthorization(completionHandler: { [self] status in
            // Tracking authorization completed. Start loading ads here.
            self.loadInterstitial()
            self.loadRewarded()
        })
    }

    func loadRewarded() {
        GADRewardedInterstitialAd.load(
            withAdUnitID: "ca-app-pub-2198867984469198/5995096543", request: GADRequest()
        ) { (ad, error) in
            if let error = error {
                print("Rewarded ad failed to load with error: \(error.localizedDescription)")
                return
            }
            print("Loading Succeeded")
            self.rewardInterstitialAd = ad
            self.rewardInterstitialAd?.fullScreenContentDelegate = self
        }
    }

    func loadInterstitial() {
        let request = GADRequest()
        GADInterstitialAd.load(withAdUnitID: "ca-app-pub-2198867984469198/9599337622",
            request: request,
            completionHandler: { [self] ad, error in
                if let error = error {
                    print("Failed to load interstitial ad with error: \(error.localizedDescription)")
                    return
                }
                interstitial = ad
                interstitial?.fullScreenContentDelegate = self
            }
        )
    }

    @IBAction func rewardAdTouched() {
        if let ad = rewardInterstitialAd {
            ad.present(
                fromRootViewController: self,
                userDidEarnRewardHandler: {
                    let reward = ad.adReward
                    Analytics.logEvent(AnalyticsEventLevelEnd, parameters: [
                        AnalyticsParameterItemID: "Reward received with currency \(reward.amount), amount \(reward.amount.doubleValue)",
                    ])
                }
            )
        } else {
            //Failed
            Analytics.logEvent(AnalyticsEventLevelEnd, parameters: [
                AnalyticsParameterItemID: "reward ad didn't loaded",
            ])
            interAdTouched()
        }
        loadRewarded()
    }

    @IBAction func interAdTouched() {
        if interstitial != nil {
            interstitial!.present(fromRootViewController: self)
        } else {
            Analytics.logEvent(AnalyticsEventLevelEnd, parameters: [
                AnalyticsParameterItemID: "intestitial ad didn't loaded",
            ])
        }
        loadInterstitial()
    }

//    func adDidPresentFullScreenContent(_ ad: GADFullScreenPresentingAd) {
//        print("Rewarded ad presented.")
//    }

    func adDidDismissFullScreenContent(_ ad: GADFullScreenPresentingAd) {
        print("Ad dismiss full screen ad")
        if type(of: ad) == GADInterstitialAd.self {
            print("InterstitialAd")
        } else if type(of: ad) == GADRewardedAd.self {
            print("RewardedAd")
        } else if type(of: ad) == GADRewardedInterstitialAd.self {
            print("Rewarded InterstitialAd")
        }
    }

    func ad(
        _ ad: GADFullScreenPresentingAd,
        didFailToPresentFullScreenContentWithError error: Error
    ) {
        print("Rewarded ad failed to present with error: \(error.localizedDescription).")
    }
}
