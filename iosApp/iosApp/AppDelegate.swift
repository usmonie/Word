//
//  AppDelegate.swift
//  iosApp
//
//  Created by Usman Akhmedov on 11/14/23.
//

import FirebaseCore
import UIKit
import GoogleMobileAds

class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
      FirebaseApp.configure()
      GADMobileAds.sharedInstance().start(completionHandler: nil)

      return true
  }
}
