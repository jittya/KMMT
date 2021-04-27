//
//  ViewController.swift
//  KMMT App
//
//  Created by Innovateq on 26/04/2021.
//

import UIKit
import shared

class ViewController: KMMUIViewController ,LoginView {
    func showErrorMessageOnUsername(errorMsg: String) {
        usernameTF.errorMessage=errorMsg
    }
    
    
    func setLoginButtonLabel(loginLabel: String) {
        loginBtn.setTitle(loginLabel, for: UIControl.State.normal)
    }
    
    func setLoginButtonClickAction(onLoginClick: @escaping () -> KotlinUnit) {
        loginBtn.setClickAction(action: onLoginClick)
    }
    
    func setPasswordLabel(passwordLabel: String) {
        passwordTF.placeholder=passwordLabel
    }
    
    func setUsernameLabel(usernameLabel: String) {
        usernameTF.placeholder=usernameLabel
    }
    
    func getEnteredPassword() -> String {
        return passwordTF.text ?? ""
    }
    
    func getEnteredUsername() -> String {
        usernameTF.errorMessage=""
        return usernameTF.text ?? ""
    }
    

    @IBOutlet weak var usernameTF: UITextFieldX!
    @IBOutlet weak var passwordTF: UITextFieldX!
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
    
    //Generated Methods from LoginViewModel
    override func initializeViewModel() -> BaseViewModel<BaseView> {
        return LoginViewModel(view: self).getViewModel()
    }

}

