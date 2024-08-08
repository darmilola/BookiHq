import Foundation
import UIKit
import SwiftUI
import CloudKit
import composeApp
import GoogleSignIn
import FirebaseCore
import FirebaseAuth
import FirebaseStorage



class AppStartViewController: UIViewController, PlatformNavigator, UINavigationControllerDelegate, UIImagePickerControllerDelegate, CLLocationManagerDelegate  {
  
    var mainController: MainViewController?
    var rootView: AppStartView?
    var mainView: MainScreenView?
    var uploadImage: UIImage?
    var imagePicker = UIImagePickerController()
    @objc dynamic var imageUploadUrl: String = ""
    @objc dynamic var locationArray: [String] = []
    var observation: NSKeyValueObservation?
    let preferences = UserDefaults.standard
    let locationManager = CLLocationManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    
    override open func viewWillAppear(_ animated: Bool){}
    
    override func viewDidAppear(_ animated: Bool){
        super.viewDidAppear(animated)
        self.navigationController?.isNavigationBarHidden = true


        if CLLocationManager.locationServicesEnabled() {
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
            locationManager.startUpdatingLocation()
        }
        showStartScreen()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let locValue: CLLocationCoordinate2D = manager.location?.coordinate else { return }
        var mLocations: [String] = []
        mLocations.append(String(locValue.latitude))
        mLocations.append(String(locValue.longitude))
        self.locationArray = mLocations
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if let pickedImage = info[.originalImage] as? UIImage {
            guard let imageData = pickedImage.jpegData(compressionQuality: 0.75) else {return}
                   let fileName = NSUUID().uuidString
                   let ref = Storage.storage().reference(withPath: "/file/\(fileName)")
                   ref.putData(imageData, metadata: nil) { metadata, error in
                       if let error = error {
                           print("Err: Failed to upload image \(error.localizedDescription)")
                           return
                       }
                       
                      ref.downloadURL { url, error in
                           guard let imageURL = url?.absoluteString else {return}
                           self.imageUploadUrl = imageURL
                       }
                   }
        }
        dismiss(animated: true, completion: nil)
    }
    
    func getCurrentLocation(){
        locationManager.requestWhenInUseAuthorization()
    }

    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        // Handle the user canceling the image picker, if needed.
        dismiss(animated: true, completion: nil)
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
            onAuthSuccessful("bellashopdevprocess@gmail.com")
        }
        
    }
    
    func startImageUpload(onUploadDone: @escaping (String) -> Void) {
        if UIImagePickerController.isSourceTypeAvailable(.savedPhotosAlbum){
                   imagePicker.delegate = self
                   imagePicker.sourceType = .savedPhotosAlbum
                   imagePicker.allowsEditing = false

            present(imagePicker, animated: true, completion: {})
        }
    
        observation = self.observe(\.imageUploadUrl, options: [.old, .new]) { controller, value in
                   onUploadDone(value.newValue!)
            }
    }
    
    func startNotificationService(onTokenReady: @escaping (String) -> Void) {
        let fcmTokenKey = "fcmToken"
        if preferences.object(forKey: fcmTokenKey) == nil {
            print("Nothing Here")
        } else {
            let fcmToken = preferences.string(forKey: fcmTokenKey)
            print("Token is")
            print(fcmToken!)
            onTokenReady(fcmToken!)
        }
    }
    
    func startPhoneSS0(phone: String) {
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
    
    func getUserLocation(onLocationReady: @escaping (String, String) -> Void) {
        self.locationManager.requestAlwaysAuthorization()
        self.locationManager.requestWhenInUseAuthorization()
        
        observation = self.observe(\.locationArray, options: [.old, .new]) { controller, value in
            onLocationReady(self.locationArray[0],self.locationArray[1])
        }
       
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




