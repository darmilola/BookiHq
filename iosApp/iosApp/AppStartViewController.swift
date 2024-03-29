import UIKit
import SwiftUI
import ComposeApp
import Auth0
import JWTDecode
import Cloudinary
import CloudKit




class AppStartViewController: UIViewController  {
    
    var locationManager: CLLocationManager?
    var mainController: MainViewController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = ZazzyApp.Colors.white
    }
    
    
    override open func viewWillAppear(_ animated: Bool){}
    
    override func viewDidAppear(_ animated: Bool){
        super.viewDidAppear(animated)
        handleLoadData()
    }
    
    private func handleLoadData() {
        mainController = getMainController()
        locationManager = CLLocationManager()
        locationManager?.delegate = self
        let appStartView = UIHostingController(rootView: AppStartView(locationManager: locationManager!, mainController: mainController!))
        showNextScreen(nextViewController: appStartView)
    }
    
    func showNextScreen(nextViewController: UIViewController?) {
        guard let viewController = nextViewController else { return }
        viewController.view.backgroundColor = ZazzyApp.Colors.white
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(5), execute: {
            self.navigationController?.pushViewController(nextViewController!, animated: true)
        })
    }
    
    func getMainController() -> MainViewController {
        return MainViewController()
    }
}
    
    struct AppStartView: UIViewControllerRepresentable {
        var locationManager: CLLocationManager
        var mainController: MainViewController
        func makeUIViewController(context: Context) -> UIViewController {
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
                },
                onLocationEvent : {
                    locationManager.requestWhenInUseAuthorization()
                    locationManager.requestLocation()
                    
                }
                
            )
            
        }
        
        func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
        
    }

    
 extension AppStartViewController: CLLocationManagerDelegate{
        public func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
            guard let location = locations.last else { return }
            mainController!.onLocationRequestAllowed(isAllowed: false)
            mainController!.onLocationResponse(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)
            print("Latitude: \(location.coordinate.latitude), Longitude: \(location.coordinate.longitude)")
        }
        
        public func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
            switch manager.authorizationStatus {
            case .notDetermined:
                print("When user did not yet determined")
            case .restricted:
                print("Restricted by parental control")
            case .denied:
                print("When user select option Dont't Allow")
            case .authorizedWhenInUse:
                print("When user select option Allow While Using App or Allow Once")
                mainController!.onLocationRequestAllowed(isAllowed: true)
            default:
                print("default")
            }
        }
        
        public func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
            // locationManager.stopUpdatingLocation()
            if let clErr = error as? CLError {
                switch clErr.code {
                case .locationUnknown, .denied, .network:
                    print("Location request failed with error: \(clErr.localizedDescription)")
                case .headingFailure:
                    print("Heading request failed with error: \(clErr.localizedDescription)")
                case .rangingUnavailable, .rangingFailure:
                    print("Ranging request failed with error: \(clErr.localizedDescription)")
                case .regionMonitoringDenied, .regionMonitoringFailure, .regionMonitoringSetupDelayed, .regionMonitoringResponseDelayed:
                    print("Region monitoring request failed with error: \(clErr.localizedDescription)")
                default:
                    print("Unknown location manager error: \(clErr.localizedDescription)")
                }
            } else {
                print("Unknown error occurred while handling location manager error: \(error.localizedDescription)")
            }
        }
    }




