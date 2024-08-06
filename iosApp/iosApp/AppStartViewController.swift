import Foundation
import UIKit
import SwiftUI
import CloudKit
import composeApp
import GoogleSignIn
import FirebaseCore
import FirebaseAuth



class AppStartViewController: UIViewController, PlatformNavigator  {
    
    var locationManager: CLLocationManager?
    var mainController: MainViewController?
    var rootView: AppStartView?
    var mainView: MainScreenView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    
    override open func viewWillAppear(_ animated: Bool){}
    
    override func viewDidAppear(_ animated: Bool){
        super.viewDidAppear(animated)
        self.navigationController?.isNavigationBarHidden = true
        showStartScreen()
    }
    
    private func showStartScreen() {
        rootView = AppStartView(platformNavigator: self)
        let appStartView = UIHostingController(rootView: rootView.edgesIgnoringSafeArea(.all))
        showNextScreen(nextViewController: appStartView)
    }
    
    func showNextScreen(nextViewController: UIViewController?) {
        guard nextViewController != nil else { return }
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(1), execute: {
            self.navigationController?.pushViewController(nextViewController!, animated: true)
        })
    }
    
    struct AppStartView: UIViewControllerRepresentable {
        var platformNavigator: PlatformNavigator
        func makeUIViewController(context: Context) -> UIViewController {
            return MainViewController().appStartUiView(platformNavigator: platformNavigator)
        }
        
        func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
        
    }
    
    struct MainScreenView: UIViewControllerRepresentable {
        var platformNavigator: PlatformNavigator
        func makeUIViewController(context: Context) -> UIViewController {
            return MainViewController().mainScreenUiView(platformNavigator: platformNavigator)
        }
        
        func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
        
    }
    
    func getUserLocation() {
      
    }
    
    func sendAppointmentBookingNotification(customerName: String, vendorLogoUrl: String, businessName: String, appointmentDay: String, appointmentMonth: String, appointmentYear: String, appointmentTime: String, serviceType: String, fcmToken: String) {
        
    }
    
    func sendConnectVendorNotification(customerName: String, vendorLogoUrl: String, fcmToken: String) {
        
    }
    
    func sendCustomerExitNotification(exitReason: String, vendorLogoUrl: String, fcmToken: String) {
        
    }
    
    func sendMeetingBookingNotification(customerName: String, vendorLogoUrl: String, meetingDay: String, meetingMonth: String, meetingYear: String, meetingTime: String, fcmToken: String) {
        
    }
    
    func sendOrderBookingNotification(customerName: String, vendorLogoUrl: String, fcmToken: String) {
        
    }
    
    func sendPostponedAppointmentNotification(customerName: String, vendorLogoUrl: String, businessName: String, appointmentDay: String, appointmentMonth: String, appointmentYear: String, appointmentTime: String, serviceType: String, fcmToken: String) {
        
    }
    
    func startGoogleSSO(onAuthSuccessful: @escaping (String) -> Void, onAuthFailed: @escaping () -> Void) {
       
        guard let clientID = FirebaseApp.app()?.options.clientID else { return }

        // Create Google Sign In configuration object.
        let config = GIDConfiguration(clientID: clientID)
        GIDSignIn.sharedInstance.configuration = config
        GIDSignIn.sharedInstance.signIn(withPresenting: self) { signInResult, error in
            onAuthSuccessful("damilolaakinterinwa@gmail.com")
        }
        
        
    }
    
    func startImageUpload(onUploadDone: @escaping (String) -> Void) {
        
    }
    
    func startNotificationService(onTokenReady: @escaping (String) -> Void) {
       
    }
    
    func startPhoneSS0(phone: String) {
        print("Started")
        PhoneAuthProvider.provider()
          .verifyPhoneNumber(phone, uiDelegate: nil) { verificationID, error in
              if let error = error {
                return
              }
           UserDefaults.standard.set(verificationID, forKey: "authVerificationID")
              print(verificationID!)
        }
    }
    
    func startScanningBarCode(onCodeReady: @escaping (String) -> Void) {
        
    }
    
    func startVideoCall(authToken: String) {
        
    }
    
    func startXSSO(onAuthSuccessful: @escaping (String) -> Void, onAuthFailed: @escaping () -> Void) {
        
    }
    
    func verifyOTP(verificationCode: String, onVerificationSuccessful: @escaping (String) -> Void, onVerificationFailed: @escaping () -> Void) {
        let verificationID = UserDefaults.standard.string(forKey: "authVerificationID")
        
        let credential = PhoneAuthProvider.provider().credential(
          withVerificationID: verificationID!,
          verificationCode: verificationCode)
        
        Auth.auth().signIn(with: credential) { (authResult, error) in
            if let error = error {
                let authError = error as NSError
                onVerificationFailed()
            }
            let currentUser = Auth.auth().currentUser
            onVerificationSuccessful(currentUser!.phoneNumber!)
        }
    }
    
   func exitApp() {}

   func goToMainScreen() {
       mainView = MainScreenView(platformNavigator: self)
       let mainScreenView = UIHostingController(rootView: mainView)
       showNextScreen(nextViewController: mainScreenView)
   }
    
   func restartApp() {
       rootView = AppStartView(platformNavigator: self)
       let appStartView = UIHostingController(rootView: rootView.edgesIgnoringSafeArea(.all))
       showNextScreen(nextViewController: appStartView)
    }
}




