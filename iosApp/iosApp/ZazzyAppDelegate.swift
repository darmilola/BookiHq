//
//  ZazzyAppDelegate.swift
//  iosApp
//
//  Created by Damilola Akinterinwa on 28/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit

@UIApplicationMain
class ZazzyAppDelegate: PluggableApplicationDelegate, WindowProvider {
    
    var mWindow: UIWindow? = UIWindow(frame: UIScreen.main.bounds)
   
 override var services: [ApplicationService] {
        return [
            UISetupApplicationService(windowProvider: self),
            ApplicationStateService(windowProvider: self)
        ]
    }
    
    @objc static var shared: ZazzyAppDelegate {
        return UIApplication.shared.delegate as! ZazzyAppDelegate
    }
    
}
