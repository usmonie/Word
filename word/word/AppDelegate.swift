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
      
      #if DEBUG
        FirebaseApp.configure()
      #else
          print(parameters)
      #endif
      GADMobileAds.sharedInstance().requestConfiguration.testDeviceIdentifiers = [ 
        "2c3eab925250535fcf0c811081116df4"
      ]
      GADMobileAds.sharedInstance().start(completionHandler: nil)

      return true
  }
}
