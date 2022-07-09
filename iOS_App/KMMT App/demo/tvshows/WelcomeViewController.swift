//
//  ViewController.swift
//  KMMT App
//
//  Created by Jitty on 26/04/2021.
//

import UIKit
import shared

class WelcomeViewController: KMMUIViewController ,WelcomeView {
   
    
    override func setPageTitle(title: String) {
        self.title=title
    }

    @IBOutlet weak var textlabel: UILabel!
    @IBOutlet weak var welcomeBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    //Generated Methods from WelcomeView
    func navigateToTVShowsPage() {
        openViewController(newViewControllerName: "TVShowsViewController")
    }
    
    func setTVShowsButtonClickAction(onLoginClick: @escaping () -> KotlinUnit) {
        welcomeBtn.addActionOnPress(handler: onLoginClick)
    }
    
    func setTVShowsButtonLabel(tvShowsBtnLbl: String) {
        welcomeBtn.setTitle(tvShowsBtnLbl, for: UIControl.State.normal)
    }
    
    func setWelcomePageLabel(msg: String) {
        textlabel.text=msg
    }
    
    //Generated Methods from KMMUIViewController
    override func initializePresenter() -> BasePresenter<BaseView> {
        return WelcomePresenter(view: self).getPresenter()
    }

}

