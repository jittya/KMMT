//
//  UITextFieldControl.swift
//  KMMTApp
//
//  Created by JittyKunju on 04/11/21.
//

import Foundation
import UIKit
import shared

extension UITextField {
    
    
    func onTextChanged(handler: @escaping (String) -> KotlinUnit) {
            self.addAction(UIAction(
                title: "",
                image: nil,
                handler: { _ in
                    handler(self.text!)
                }
            ),for: .editingChanged)
        }

}
