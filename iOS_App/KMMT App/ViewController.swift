//
//  ViewController.swift
//  KMMT App
//
//  Created by Innovateq on 26/04/2021.
//

import UIKit
import shared

class ViewController: KMMUIViewController ,LoginView {
    
    

    @IBOutlet weak var textlabel: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
    }

    //Generated Methods from LoginView
    func showGreetMsg(msg: String) {
        textlabel.text=msg
    }
    
    //Generated Methods from LoginViewModel
    override func initializeViewModel() -> BaseViewModel<BaseView> {
        return LoginViewModel(view: self).getViewModel()
    }

}

