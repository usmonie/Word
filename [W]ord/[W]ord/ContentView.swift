//
//  ContentView.swift
//  [W]ord
//
//  Created by Usman Akhmedov on 11/13/23.
//

import SwiftUI
import ComposeApp
import design

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                .edgesIgnoringSafeArea(.top)
                .edgesIgnoringSafeArea(.bottom)
    }
}

