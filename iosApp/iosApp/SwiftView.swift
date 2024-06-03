//
//  BannerView.swift
//  iosApp
//
//  Created by Usman Akhmedov on 6/1/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit
import SwiftUI

class SwiftView<Content: View>: UIView {
    init(content: Content) {
        super.init(frame: CGRect())
        let hostingController = UIHostingController(rootView: content)
        hostingController.view.translatesAutoresizingMaskIntoConstraints = false
        addSubview(hostingController.view)
//        NSLayoutConstraint.activate([
//            hostingController.view.topAnchor.constraint(equalTo: topAnchor),
//            hostingController.view.leadingAnchor.constraint(equalTo: leadingAnchor),
//            hostingController.view.trailingAnchor.constraint(equalTo: trailingAnchor),
//            hostingController.view.bottomAnchor.constraint(equalTo: bottomAnchor)
//        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
