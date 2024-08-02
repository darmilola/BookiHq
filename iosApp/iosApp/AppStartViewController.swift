import Foundation
import UIKit
import SwiftUI
import CloudKit
import composeApp



class AppStartViewController: UIViewController  {
    
    var locationManager: CLLocationManager?
    var mainController: MainViewController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    
    override open func viewWillAppear(_ animated: Bool){}
    
    override func viewDidAppear(_ animated: Bool){
        super.viewDidAppear(animated)
        handleLoadData()
    }
    
    private func handleLoadData() {
        mainController = getMainController()
        let appStartView = UIHostingController(rootView: AppStartView().edgesIgnoringSafeArea(.all))
        showNextScreen(nextViewController: appStartView)
    }
    
    func showNextScreen(nextViewController: UIViewController?) {
        guard let viewController = nextViewController else { return }
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(5), execute: {
            self.navigationController?.pushViewController(nextViewController!, animated: true)
        })
    }
    
    func getMainController() -> MainViewController {
        return MainViewController()
    }
    
    
    struct AppStartView: UIViewControllerRepresentable {
        func makeUIViewController(context: Context) -> UIViewController {
            return MainViewController().MainUIViewController(
                onLoginEvent: { connectionType in
                    
                },
                onLogoutEvent: {connectionType in
                    
                },
                onSignupEvent: { connectionType in
                    
                },
                
                onUploadImageEvent: { data in
                    
                },
                onLocationEvent : {
                    
                    
                }
                
            )
            
        }
        
        func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
        
    }
}




