import UIKit
import GoogleMobileAds

class InterstitalRewardedViewController: UIViewController, GADFullScreenContentDelegate {
    @IBOutlet weak var btnRwdClick: UIButton!
    @IBOutlet weak var btnInters: UIButton!
    //var rewadAd: GADRewardedAd?
    var rewardInterstitialAd: GADRewardedInterstitialAd?
    var interstitial: GADInterstitialAd?

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        GADRewardedInterstitialAd.load(
            withAdUnitID: "ca-app-pub-3940256099942544/6978759866", request: GADRequest()
        ) { (ad, error) in
            if let error = error {
                print("Rewarded ad failed to load with error: \(error.localizedDescription)")
                return
            }
            print("Loading Succeeded")
            self.rewardInterstitialAd = ad
            self.rewardInterstitialAd?.fullScreenContentDelegate = self
        }
        let request = GADRequest()
        GADInterstitialAd.load(withAdUnitID: "ca-app-pub-3940256099942544/4411468910",
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

    @IBAction func rewadAdTouched(_ sender: Any) {
        if let ad = rewardInterstitialAd {

            ad.present(fromRootViewController: self,
                userDidEarnRewardHandler: {
                    let reward = ad.adReward
                    // TODO: Reward the user.
                    print("Reward received with currency \(reward.amount), amount \(reward.amount.doubleValue)")
                }
            )
        } else {
            //Failed
            print("reward didn't loaded")
        }
    }

    @IBAction func interAdTouched(_ sender: Any) {
        if interstitial != nil {
            interstitial!.present(fromRootViewController: self)
        } else {
            print("Ad wasn't ready")
        }
    }

    func adDidPresentFullScreenContent(_ ad: GADFullScreenPresentingAd) {
        print("Rewarded ad presented.")
    }

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