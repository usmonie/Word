//
//  _W_ordApp.swift
//  iosApp
//
//  Created by Usman Akhmedov on 11/13/23.
//

import SwiftUI
import SwiftData
import AppTrackingTransparency

@main
struct WordApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
     
    @Environment(\.scenePhase) var scenePhase

    var body: some Scene {
        WindowGroup {
            ContentView()
                .onChange(of: scenePhase, perform: { newValue in
                    if newValue == .active {
                        ATTrackingManager.requestTrackingAuthorization { status in
                            // do something
                        }
                    }
                })
        }
    }
}
