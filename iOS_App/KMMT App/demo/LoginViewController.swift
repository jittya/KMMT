//
//  ViewController.swift
//  KMMT App
//
//  Created by Innovateq on 26/04/2021.
//

import UIKit
import shared

class LoginViewController: KMMUIViewController ,LoginView {
    

    
    func navigateToHomePage(bundleParcel: shared.BundleParcel) {
        openViewController(newViewControllerName: "HomeViewController",bundle: bundleParcel)
    }
    
    
    @IBOutlet weak var usernameTF: UITextField!
    @IBOutlet weak var passwordTF: UITextField!
    @IBOutlet weak var textlabel: UILabel!
    @IBOutlet weak var loginBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    //Generated Methods from LoginView
    func setLoginPageLabel(msg: String) {
        textlabel.text=msg
    }
    
    func setUsernameLabel(usernameLabel: String) {
        usernameTF.placeholder=usernameLabel
    }
    
    func setPasswordLabel(passwordLabel: String) {
        passwordTF.placeholder=passwordLabel
    }
    
    func getEnteredUsername() -> String {
        usernameTF.setError()
        return usernameTF.text ?? ""
    }
    
    func getEnteredPassword() -> String {
        return passwordTF.text ?? ""
    }
    
    func setLoginButtonClickAction(onLoginClick: @escaping () -> KotlinUnit) {
        loginBtn.addActionOnPress(handler: onLoginClick)
    }
    
    func setLoginButtonLabel(loginLabel: String) {
        loginBtn.setTitle(loginLabel, for: UIControl.State.normal)
    }
    
    func showErrorMessageOnUsername(errorMsg: String) {
        usernameTF.setError(errorMsg,show: true)
    }
    
    //Generated Methods from KMMUIViewController
    override func initializePresenter() -> BasePresenter<BaseView> {
        return LoginPresenter(view: self).getPresenter()
    }

}

