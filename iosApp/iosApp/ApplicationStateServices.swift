//
//  ApplicationStateServices.swift
//  iosApp
//
//  Created by Damilola Akinterinwa on 28/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit
import Firebase
import GoogleSignIn
import FirebaseCore


class ApplicationStateService: NSObject, ApplicationService, UNUserNotificationCenterDelegate, MessagingDelegate {

    private let windowProvider: WindowProvider
    let preferences = UserDefaults.standard

    
    init(windowProvider: WindowProvider) {
        self.windowProvider = windowProvider
    }

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        FirebaseApp.configure()
        
        UNUserNotificationCenter.current().delegate = self

        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        UNUserNotificationCenter.current().requestAuthorization(
          options: authOptions,
          completionHandler: { _, _ in }
        )

        application.registerForRemoteNotifications()
        
        Messaging.messaging().delegate = self
        
        Messaging.messaging().token { [self] token, error in
            print("Yes I Got Here")
          if let error = error {
            print("Error fetching FCM registration token: \(error)")
          } else if let token = token {
              let fcmTokenKey = "fcmToken"
              self.preferences.set(token, forKey: fcmTokenKey)
              print("Saved Here")
            }
        }
        
        return true
    }
    
    func application(_ app: UIApplication,
                     open url: URL,
                     options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
      return GIDSignIn.sharedInstance.handle(url)
    }
    
    func applicationWillEnterForeground(_ application: UIApplication) {
        
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        application.applicationIconBadgeNumber = 0
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        
    }
    
}

