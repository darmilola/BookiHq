import Foundation
import UIKit
import SwiftUI
import CloudKit
import composeApp



class AppStartViewController: UIViewController, PlatformNavigator  {
    
    var locationManager: CLLocationManager?
    var mainController: MainViewController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    
    override open func viewWillAppear(_ animated: Bool){}
    
    override func viewDidAppear(_ animated: Bool){
        super.viewDidAppear(animated)
        showStartScreen()
    }
    
    private func showStartScreen() {
        let appStartView = UIHostingController(rootView: AppStartView(platformNavigator: self).edgesIgnoringSafeArea(.all))
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
    
    struct WelcomeScreenView: UIViewControllerRepresentable {
        var platformNavigator: PlatformNavigator
        func makeUIViewController(context: Context) -> UIViewController {
            return MainViewController().welcomePageUiView(platformNavigator: platformNavigator)
        }
        
        func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
        
    }
    
    func getUserLocation() {
        print("Here")
    }
    
    func sendAppointmentBookingNotification(customerName: String, vendorLogoUrl: String, businessName: String, appointmentDay: String, appointmentMonth: String, appointmentYear: String, appointmentTime: String, serviceType: String, fcmToken: String) {
        print("Here")
    }
    
    func sendConnectVendorNotification(customerName: String, vendorLogoUrl: String, fcmToken: String) {
        print("Here")
    }
    
    func sendCustomerExitNotification(exitReason: String, vendorLogoUrl: String, fcmToken: String) {
        print("Here")
    }
    
    func sendMeetingBookingNotification(customerName: String, vendorLogoUrl: String, meetingDay: String, meetingMonth: String, meetingYear: String, meetingTime: String, fcmToken: String) {
        print("Here")
    }
    
    func sendOrderBookingNotification(customerName: String, vendorLogoUrl: String, fcmToken: String) {
        print("Here")
    }
    
    func sendPostponedAppointmentNotification(customerName: String, vendorLogoUrl: String, businessName: String, appointmentDay: String, appointmentMonth: String, appointmentYear: String, appointmentTime: String, serviceType: String, fcmToken: String) {
        print("Here")
    }
    
    func startGoogleSSO(onAuthSuccessful: @escaping (String) -> Void, onAuthFailed: @escaping () -> Void) {
       print("Here")
    }
    
    func startImageUpload(onUploadDone: @escaping (String) -> Void) {
        print("Here")
    }
    
    func startNotificationService(onTokenReady: @escaping (String) -> Void) {
        print("Here")
    }
    
    func startPhoneSS0(phone: String) {
        print("Here")
    }
    
    func startScanningBarCode(onCodeReady: @escaping (String) -> Void) {
        print("Here")
    }
    
    func startVideoCall(authToken: String) {
        print("Here")
    }
    
    func startXSSO(onAuthSuccessful: @escaping (String) -> Void, onAuthFailed: @escaping () -> Void) {
        print("Here")
    }
    
    func verifyOTP(verificationCode: String, onVerificationSuccessful: @escaping (String) -> Void, onVerificationFailed: @escaping () -> Void) {
        print("Here")
    }
}




