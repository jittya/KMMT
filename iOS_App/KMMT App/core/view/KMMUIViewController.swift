//
//  BaseUIViewController.swift
//  KMMT App
//
//  Created by Innovateq on 26/04/2021.
//

import Foundation
import UIKit
import shared



class KMMUIViewController :UIViewController
{
    private var viewModel: BaseViewModel<BaseView>? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        viewModel = initializeViewModel()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        viewModel?.onInit()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        viewModel?.onDetached()
    }
    
    func getViewModel() -> BaseViewModel<BaseView>? {
           return viewModel
       }

    func initializeViewModel() -> BaseViewModel<BaseView>
    {
            preconditionFailure("This method must be overridden")
    }
    
    @objc(showPopUpMessageMessage:) func showPopUpMessage(message: String) {
        let alert = UIAlertController(title: "", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true)
    }
    
    @objc(showPopUpMessageTitle:message:) func showPopUpMessage(title:String,message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true)
    }
}
