//
//  HomeViewController.swift
//  KMMTApp
//
//  Created by Jitty on 29/04/2021.
//

import UIKit
import shared
import Foundation

class TVShowsViewController: KMMUIViewController,TVShowsSearchView  {

  
    override func setPageTitle(title: String) {
    }
    
    var showList: [TVShowInfo] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        postTableView.dataSource = self
        
    }

    @IBOutlet weak var searchTF: UITextField!
    @IBOutlet weak var postTableView: UITableView!
    
    override func initializePresenter() -> BasePresenter<BaseView> {
        return TVShowsSearchPresenter(view: self,tvShowSearchUseCase: TVShowSearchRepoUseCase(repository: TVShowSearchRepositoryTVMazeAPI())).getPresenter()
    }
    
    func setSearchQueryChangeListener(onSearchQueryStringChanged: @escaping (String) -> KotlinUnit) {
        searchTF.onTextChanged(handler: onSearchQueryStringChanged)
    }
    
    func showTVShowsList(tvShowList: [TVShowInfo]) {
        self.showList = tvShowList
        postTableView.reloadData()
    }
    
}

