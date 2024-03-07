import UIKit
import SwiftUI
import ComposeApp
import Auth0
import JWTDecode

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        var mainController = getMainController()
       
        return mainController.MainViewController(
            onLoginEvent: { connectionType in
                Auth0
                 .webAuth()
                 .redirectURL(URL(string: "com.application.zazzy.Zazzy://dev-6s0tarpbfr017qxp.us.auth0.com/ios/com.application.zazzy.Zazzy/callback")!)
                 .connection(connectionType)
                 .start { result in
                     switch result {
                     case .success(let credentials):
                         let jwt = try? decode(jwt: credentials.idToken)
                         mainController
                             .setAuthResponse(response: Auth0ConnectionResponse(connectionType: connectionType, email: jwt?["email"].string ?? "", action: "login", status: "success"))
                                                                                                
                     case .failure(let error):
                            mainController
                              .setAuthResponse(response: Auth0ConnectionResponse(connectionType: connectionType, email: "", action: "login", status: "failure"))
                     }
                 }
            },
            onLogoutEvent: {connectionType in
                Auth0
                    .webAuth()
                    .redirectURL(URL(string: "com.application.zazzy.Zazzy://dev-6s0tarpbfr017qxp.us.auth0.com/ios/com.application.zazzy.Zazzy/callback")!)
                    .clearSession { result in
                        switch result {
                        case .success:
                            mainController
                              .setAuthResponse(response: Auth0ConnectionResponse(connectionType: connectionType, email: "", action: "logout", status: "success"))
                        case .failure(let error):
                            mainController
                              .setAuthResponse(response: Auth0ConnectionResponse(connectionType: connectionType, email: "", action: "logout", status: "failure"))
                        }
                    }
                 },
            onSignupEvent: { connectionType in
                Auth0
                 .webAuth()
                 .redirectURL(URL(string: "https://com.application.zazzy.Zazzy://dev-6s0tarpbfr017qxp.us.auth0.com/ios/com.application.zazzy.Zazzy/callback")!)
                 .connection(connectionType)
                 .start { result in
                     switch result {
                     case .success(let credentials):
                         let jwt = try? decode(jwt: credentials.idToken)
                         mainController
                             .setAuthResponse(response: Auth0ConnectionResponse(connectionType: connectionType, email: jwt?["email"].string ?? "", action: "signup", status: "success"))
                                                                                                
                     case .failure(let error):
                         mainController
                             .setAuthResponse(response: Auth0ConnectionResponse(connectionType: connectionType, email:  "", action: "signup", status: "failure"))
                     }
                 }
            })
        
      
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
    
    func getMainController() -> MainViewController {
        return MainViewController()
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all, edges: [.bottom, .top]) // Compose has own keyboard handler
    }
    
}




