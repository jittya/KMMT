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
    
    func getViewModel() -> BaseViewModel<BaseView>? {
           return viewModel
       }

    func initializeViewModel() -> BaseViewModel<BaseView>
    {
            preconditionFailure("This method must be overridden")
    }
}
