//
//  NativeAnalytics.swift
//  iosApp
//
//  Created by Usman Akhmedov on 11/17/23.
//

import Foundation
import ComposeApp
import FirebaseAnalytics

class NativeAnalytics: DomainAnalytics {
    override func log(analyticsEvent: DomainAnalyticsEvent) {
        // Преобразование данных EventData в словарь для Firebase Analytics
        let parameters = convertEventDataToDictionary(key: analyticsEvent.key, data: analyticsEvent.data)
        // Использование Firebase Analytics для отправки события
        Analytics.logEvent(analyticsEvent.key, parameters: parameters)
    }

    private func convertEventDataToDictionary(key: String, data: DomainAnalyticsEventEventData) -> [String: Any] {
        // Здесь вам нужно преобразовать ваш EventData в словарь [String: Any]
        // Это будет зависеть от структуры ваших данных
        return [key: data]
    }
}


