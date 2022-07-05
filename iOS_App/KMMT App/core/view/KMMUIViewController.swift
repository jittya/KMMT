//
//  BaseUIViewController.swift
//  KMMT App
//
//  Created by Jitty on 26/04/2021.
//

import Foundation
import UIKit
import shared



class KMMUIViewController :UIViewController
{
    private var presenter: BasePresenter<BaseView>? = nil
    var bundle:BundleParcel? = nil
    private let kLifecycle = LiveDataLifecycle()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presenter = initializePresenter()
        presenter?.setLifeCycle(lifeCycle: kLifecycle)
        if (bundle != nil){
            presenter?.setBundle(bundle: bundle!.extras)
        }
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        presenter?.onInit()
    }
    
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        presenter?.onDetached()
    }
    
    func getPresenter() -> BasePresenter<BaseView>? {
        return presenter
    }
    
    func initializePresenter() -> BasePresenter<BaseView>
    {
        preconditionFailure("This method must be overridden Eg: return LoginViewModel(view: self).getViewModel()")
    }
    
    @objc(setPageTitleTitle:) func setPageTitle(title: String)
    {
        preconditionFailure("Presenter is trying to set the page title. Please override this method in your ViewContoller")
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
        removeSpinner()
    }
    
    @objc(showLoadingLoadingLabel:) func showLoading(loadingLabel: String) {
        showSpinner(onView: self.view,message: loadingLabel)
    }
    
    func openViewController(newViewControllerName: String,bundle: BundleParcel? = nil)
    {
        getViewController(newViewControllerName: newViewControllerName, bundle: bundle)
    }
    
    private func getViewController(storyboardName:String="Main", newViewControllerName:String, bundle: BundleParcel? = nil)
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
    
    var vSpinner : UIView?
    
    private func showSpinner(onView : UIView,message :String) {
        
        DispatchQueue.main.async {
            let height = 80
            
            let blufEffect = UIBlurEffect(style: .dark)
            let effectView = UIVisualEffectView(effect: blufEffect)
            
            let strLabel = UILabel(frame: CGRect(x: 60, y: 0, width: Int(onView.frame.width)-90, height: height))
            effectView.frame = CGRect(x: onView.frame.minX+10, y: onView.frame.midY - strLabel.frame.height/2 , width: onView.frame.width-20, height: CGFloat(height))
            
            strLabel.text = message
            strLabel.font = .systemFont(ofSize: 14, weight: .medium)
            strLabel.textColor = UIColor.white
            
            effectView.layer.cornerRadius = 15
            effectView.layer.masksToBounds = true
            let activityIndicator = UIActivityIndicatorView(style: .medium)
            activityIndicator.frame = CGRect(x: 10, y: (height/2)-25, width: 50, height: 50)
            activityIndicator.color=UIColor.white
            activityIndicator.startAnimating()
            effectView.contentView.addSubview(activityIndicator)
            effectView.contentView.addSubview(strLabel)
            onView.addSubview(effectView)
            self.vSpinner = effectView
        }
    }
    
    private func removeSpinner() {
        DispatchQueue.main.async {
            self.vSpinner?.removeFromSuperview()
            self.vSpinner = nil
        }
    }
}
