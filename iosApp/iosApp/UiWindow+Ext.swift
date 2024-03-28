//
//  UiWindow+Ext.swift
//  iosApp
//
//  Created by Damilola Akinterinwa on 28/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//
import UIKit

private var _portraitWindow: UIWindow?

extension UIWindow {
    @objc static func portrait() -> UIWindow {
        if let window = _portraitWindow {
            return window
        }
        let window = UIWindow(frame: UIScreen.main.bounds)
        window.makeKeyAndVisible()
        window.isHidden = false
        _portraitWindow = window
        return window
    }

    static func disposePortrait() {
        if let window = _portraitWindow {
            UIApplication.shared.delegate?.window??.makeKeyAndVisible()
            window.isHidden = true
            window.rootViewController = nil
            _portraitWindow = nil
        }
    }
}

protocol WindowProvider {
    var window: UIWindow? { get }
}

