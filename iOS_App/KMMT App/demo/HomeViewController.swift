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
    func showUsername(username: String) {
        usernameLabel.text=username
    }
    
    
    func showPostList(postList: [PostModel]) {
        
    }
    
    
    @IBOutlet weak var usernameLabel: UILabel!
    override func initializeViewModel() -> BaseViewModel<BaseView> {
        return HomeViewModel(view: self).getViewModel()
    }
}

