//
//  ExtensionsTest.swift
//  iosApp
//
//  Created by Innovateq on 14/04/2021.
//

import Foundation
import UIKit
import shared

extension UIControl {
    
    func addActionOnPress(handler: @escaping () -> KotlinUnit) {
            self.addAction(UIAction(
                title: "",
                image: nil,
                handler: { _ in
                    handler()
                }
            ),for: .touchUpInside)
        }
}
