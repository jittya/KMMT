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
    
    override func initializeViewModel() -> BaseViewModel<BaseView> {
        return HomeViewModel(view: self).getViewModel()
    }
    
}

