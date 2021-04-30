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
    var bundle:shared.BundleX? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        viewModel = initializeViewModel()
        if (bundle != nil){
            viewModel?.setBundle(bundle: bundle!.extras)
        }
        
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
            preconditionFailure("This method must be overridden Eg: return LoginViewModel(view: self).getViewModel()")
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
    
    @objc func dismissLoading() {
        
    }
    
    @objc(showLoadingLoadingLabel:) func showLoading(loadingLabel: String) {
        
    }
    
    func openViewController(newViewControllerName: String,bundle: BundleX)
    {
        getViewController(newViewControllerName: "HomeViewController", bundle: bundle)
    }
    
    private func getViewController(storyboardName:String="Main", newViewControllerName:String, bundle: BundleX)
    {
        let storyBoard: UIStoryboard = UIStoryboard(name: storyboardName, bundle: nil)
        let newViewController = storyBoard.instantiateViewController(withIdentifier: newViewControllerName)
        newViewController.modalPresentationStyle = .fullScreen
        newViewController.modalTransitionStyle = .crossDissolve
        if (newViewController is KMMUIViewController) {
            (newViewController as! KMMUIViewController).bundle=bundle
        }
        self.present(newViewController, animated: true, completion: nil)
        
    }
}
