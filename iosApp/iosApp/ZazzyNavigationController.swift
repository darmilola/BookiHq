//
//  ZazzyNavigationController.swift
//  iosApp
//
//  Created by Damilola Akinterinwa on 28/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit

@objc class ZazzyNavigationController: UINavigationController {
    
    var hideNavigationBarBottomLine: Bool = false {
        didSet {
            updateNavigationBarAppearance()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        updateNavigationBarAppearance()
    }
    
    private func updateNavigationBarAppearance() {
        let appearance = UINavigationBarAppearance()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        setNeedsStatusBarAppearanceUpdate()

        // TODO: Remove this eventually and handle modal dismissal on a case by case basis.
        if modalPresentationStyle != .popover {
            isModalInPresentation = true
        }
    }

    // MARK: Status bar

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .default
    }

    override var prefersStatusBarHidden: Bool {
        return topViewController?.prefersStatusBarHidden ?? super.prefersStatusBarHidden
    }

    // MARK: Device orientation

    override var shouldAutorotate: Bool {
        return topViewController?.shouldAutorotate ?? super.shouldAutorotate
    }

    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        return topViewController?.supportedInterfaceOrientations ?? super.supportedInterfaceOrientations
    }

    override var preferredInterfaceOrientationForPresentation: UIInterfaceOrientation {
        return topViewController?.preferredInterfaceOrientationForPresentation ?? super.preferredInterfaceOrientationForPresentation
    }
}

