import UIKit
import SwiftUI
import ComposeApp
import Auth0
import JWTDecode
import Cloudinary
import CloudKit

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        var mainController = getMainController()
       
        return mainController.MainViewController(
            onLoginEvent: { connectionType in
                Auth0
                 .webAuth()
                 .redirectURL(URL(string: "demo://dev-6s0tarpbfr017qxp.us.auth0.com/android/com.application.zazzy/callback")!)
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
                    .redirectURL(URL(string: "demo://dev-6s0tarpbfr017qxp.us.auth0.com/android/com.application.zazzy/callback")!)
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
                 .redirectURL(URL(string: "demo://dev-6s0tarpbfr017qxp.us.auth0.com/android/com.application.zazzy/callback")!)
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
            },
            
            onUploadImageEvent: { data in
                let config = CLDConfiguration(cloudName: "df2aprbbs", apiKey:"699592978195546", secure: true)
                let cloudinary = CLDCloudinary(configuration: config)
                let params = CLDUploadRequestParams()
                let request = cloudinary
                .createUploader()
                .upload(data: data, uploadPreset: "UserUploads", params: CLDUploadRequestParams())
                { progress in
                    mainController.onImageUploadProcessing(isDone: true)
                      // Handle progress
                } completionHandler: { result, error in
                   mainController.onImageUploadProcessing(isDone: false)
                    mainController.onImageUploadResponse(imageUrl: result?.secureUrl ?? "empty")
                      // Handle result
                }
            }
        
        )
        
      
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




