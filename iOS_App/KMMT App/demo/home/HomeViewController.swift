//
//  HomeViewController.swift
//  KMMTApp
//
//  Created by Innovateq on 29/04/2021.
//

import UIKit
import shared
import Foundation

class HomeViewController: KMMUIViewController,HomeView  {
    func navigateToKampKitDemoPage() {
//        self.dismiss(animated: true, completion: nil);
        openViewController(newViewControllerName: "BreedViewController")
    }
    
    func setKampKitBtnClickAction(btnClickAction: @escaping () -> KotlinUnit) {
        kampKitDemoBtn.addActionOnPress(handler: btnClickAction)
    }
    
    func setKampKitPageButtonLabel(btnLabel: String) {
        kampKitDemoBtn.setTitle(btnLabel, for: UIControl.State.normal)
    }
    
    
    var postList: [PostModel] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        postTableView.dataSource = self
        
    }
    
    func showUsername(username: String) {
        usernameLabel.text=username
    }
    
    
    func showPostList(postList: [PostModel]) {
        self.postList = postList
        postTableView.reloadData()
    }
    
    
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var postTableView: UITableView!
    @IBOutlet weak var kampKitDemoBtn: UIButton!
    
    override func initializePresenter() -> BasePresenter<BaseView> {
        return HomePresenter(view: self).getPresenter()
    }
    
}

