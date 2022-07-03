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
    private struct Holder {
          static var textChanged_KoltinUnit = [String:((String) -> KotlinUnit?)]()
       }
    
    override func getId() -> String {
        let tag=self.tag
            if tag==0 {
                let count=Holder.textChanged_KoltinUnit.count
                self.tag=Int(Date().timeIntervalSince1970.rounded())+count
            }
            return String(self.tag)
        }
    
    func addTextChangedListener(action:(@escaping (String) -> KotlinUnit?))
    {
        ontextChanged = action
    }
    
   
    
    private var ontextChanged:((String) -> KotlinUnit?)? {
            get {
                return Holder.textChanged_KoltinUnit[getId()] ?? nil
            }
            set(newValue) {
                Holder.textChanged_KoltinUnit[getId()] = newValue
                self.addTarget(self, action: #selector(textChanged(_:)), for: .editingChanged)
            }
        }
    
 
    
    @objc private func textChanged (_ sender:UITextField) {

        let result=ontextChanged?.self(sender.text ?? "")
        
        //Dummy code to remove warning
        if(result != nil)
        {
            
        }
        
    }
    

}
