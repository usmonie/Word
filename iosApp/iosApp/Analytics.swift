//
//  Analytics.swift
//  iosApp
//
//  Created by Usman Akhmedov on 6/1/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp
import AmplitudeSwift

class NativeAnalytics: AnalyticsAnalytics  {
    private let amplitude: Amplitude
    
    init() {
        #if DEBUG
        amplitude = Amplitude(configuration: Configuration(
            apiKey: "244f468ae1266bd4dbfb8fd739cdc87e"
        ))
        #else
        amplitude = Amplitude(configuration: Configuration(
            apiKey: "78f6281a02303905977f69718014b64f"
        ))
        #endif
    }

    func log(analyticsEvent: AnalyticsAnalyticsEvent) {
        // Преобразование данных EventData в словарь для Firebase Analytics
        let parameters = convertEventDataToDictionary(key: analyticsEvent.key, data: analyticsEvent.data)
        // Использование Firebase Analytics для отправки события
        
        #if DEBUG
            print(parameters)
        #else
            amplitude.track(eventType: analyticsEvent.key, eventProperties: parameters)
//            Analytics.logEvent(analyticsEvent.key, parameters: parameters)
        #endif
    }

    private func convertEventDataToDictionary(key: String, data: AnalyticsAnalyticsEventEventData) -> [String: Any] {
        // Здесь вам нужно преобразовать ваш EventData в словарь [String: Any]
        // Это будет зависеть от структуры ваших данных
        return [key: data]
    }
    
}
