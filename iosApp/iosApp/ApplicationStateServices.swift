//
//  ApplicationStateServices.swift
//  iosApp
//
//  Created by Damilola Akinterinwa on 28/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit


class ApplicationStateService: NSObject, ApplicationService {

    private let windowProvider: WindowProvider

    
    init(windowProvider: WindowProvider) {
        self.windowProvider = windowProvider
    }

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        
        return true
    }
    
    func applicationWillEnterForeground(_ application: UIApplication) {
        
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        application.applicationIconBadgeNumber = 0
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        
    }
    
}

