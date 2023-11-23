import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        var viewController: UIViewController  = MainViewControllerKt.MainViewController()
        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
    
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all, edges: [.bottom, .top]) // Compose has own keyboard handler
    }
    
}




