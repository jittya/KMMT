//
//  KampKitDemoViewController.swift
//  KMMTApp
//
//  Created by Innovateq on 02/05/2021.
//

import UIKit
import shared

class BreedViewController: KMMUIViewController ,BreedView {
    
    var invertBreedFavouriteState: ( (TBreed) -> KotlinUnit)? = nil
    var refresh: ((KotlinBoolean) -> KotlinUnit)? = nil
    
    func setBreedFavouriteClickAction(invertBreedFavouriteState: @escaping (TBreed) -> KotlinUnit) {
        self.invertBreedFavouriteState = invertBreedFavouriteState
    }
    
    func setBreedRefreshAction(refresh: @escaping (KotlinBoolean) -> KotlinUnit) {
        self.refresh=refresh
        refreshControl.addTarget(self, action: #selector(self.getBreedsForced), for: .valueChanged)
    }
    
    @objc
    func getBreedsForced() {
        if(refresh != nil)
        {
            refreshControl.beginRefreshing()
            refresh!(KotlinBoolean(bool: true))
        }
    }
    
    func stopRefreshing() {
        refreshControl.endRefreshing()
    }
    
    func refreshBreedList(breedList: [TBreed]) {
        self.breedList = breedList
        breedTableView.reloadData()
    }
    
    var breedList: [TBreed] = []

    @IBOutlet weak var breedTableView: UITableView!
    private let refreshControl = UIRefreshControl()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        breedTableView.dataSource = self
        // Add Refresh Control to Table View
        breedTableView.refreshControl = refreshControl
    }
    
    override func initializeViewModel() -> BaseViewModel<BaseView> {
        return BreedViewModel(view: self).getViewModel()
    }
}
