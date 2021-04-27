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
    private struct Holder {
           static var clickAction_KoltinUnit = [String:(() -> KotlinUnit?)]()
          static var clickActions_Void = [String:(() -> Void?)]()
       }
    
    func getId() -> String {
        let tag=self.tag
            if tag==0 {
                let count=Holder.clickAction_KoltinUnit.count+Holder.clickActions_Void.count
                self.tag=Int(Date().timeIntervalSince1970.rounded())+count
            }
            return String(self.tag)
        }
    
    func setClickAction(action:(@escaping () -> KotlinUnit?))
    {
        clickAction_KoltinUnit = action
    }
    
    func setClickAction(action:(@escaping () -> Void?))
    {
        clickAction_Void = action
    }
    
    private var clickAction_KoltinUnit:(() -> KotlinUnit?)? {
            get {
                return Holder.clickAction_KoltinUnit[getId()] ?? nil
            }
            set(newValue) {
                Holder.clickAction_KoltinUnit[getId()] = newValue
                self.addTarget(self, action: #selector(btnClicked(_:)), for: .touchUpInside)
            }
        }
    
    private var clickAction_Void:(() -> Void?)? {
            get {
                return Holder.clickActions_Void[getId()] ?? nil
            }
            set(newValue) {
                Holder.clickActions_Void[getId()] = newValue
                self.addTarget(self, action: #selector(btnClickedVoid(_:)), for: .touchUpInside)
            }
        }
    
    @objc private func btnClicked (_ sender:UIControl) {

        let result=clickAction_KoltinUnit?.self()
        
        //Dummy code to remove warning
        if(result != nil)
        {
            
        }
        
    }
    
    @objc private func btnClickedVoid (_ sender:UIControl) {

        let result: Void?=clickAction_Void?.self()
        
        //Dummy code to remove warning
        if(result != nil)
        {
            
        }
        
    }

}
