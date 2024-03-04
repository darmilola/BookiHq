import UIKit
import SwiftUI
import ComposeApp
import AuthenticationServices
import Auth0

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        var viewController: UIViewController  = MainViewController().MainViewController {
             Auth0
              .webAuth()
              .start { result in
                  switch result {
                  case .success(let credentials):
                      print("Obtained credentials: \(credentials)")
                  case .failure(let error):
                      print("Failed with: \(error)")
                  }
              }
            
        }
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




