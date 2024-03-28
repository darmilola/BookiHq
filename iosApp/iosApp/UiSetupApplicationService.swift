//
//  UiSetupApplicationService.swift
//  iosApp
//
//  Created by Damilola Akinterinwa on 28/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//


import UIKit
import SwiftUI
import Foundation


class UISetupApplicationService: NSObject, ApplicationService {
    let windowProvider: WindowProvider
    
    init(windowProvider: WindowProvider) {
        self.windowProvider = windowProvider
    }
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        return true
    }
    
    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession,
       options: UIScene.ConnectionOptions
    ) -> UISceneConfiguration {
       let sceneConfig = UISceneConfiguration(name: "ZazzyAppScene", sessionRole: connectingSceneSession.role)
       sceneConfig.delegateClass = ZazzyAppSceneDelegate.self
       return sceneConfig
     }

    func application(_ application: UIApplication,
                     supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
        // device logic
        if let topController = window?.rootViewController as? UINavigationController {
            if let vc = topController.visibleViewController {
                if vc.supportedInterfaceOrientations == .all {
                    return .portrait
                } else {
                    return vc.supportedInterfaceOrientations
                }
            } else {
                return .portrait
            }
        }
        return .portrait
    }
    
}
