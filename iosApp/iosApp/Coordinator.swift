//
//  Coordinator.swift
//  iosApp
//
//  Created by Damilola Akinterinwa on 28/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit

@objc protocol Coordinator {
    var childCoordinators: [Coordinator] { get set }
    var navigationController: ZazzyNavigationController { get set }

    @discardableResult
    func start() -> UIViewController
}

@objc protocol DismissCoordinator: AnyObject {
    @objc func dismiss(viewController: UIViewController)
    @objc optional func popViewController(using navigationController: UINavigationController?)
    @objc optional func popToRootViewController(using navigationController: UINavigationController?)
}

