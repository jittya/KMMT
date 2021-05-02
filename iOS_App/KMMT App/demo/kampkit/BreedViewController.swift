//
//  KampKitDemoViewController.swift
//  KMMTApp
//
//  Created by Innovateq on 02/05/2021.
//

import UIKit
import shared

class BreedViewController: KMMUIViewController ,BreedView {
    func refreshBreedList(breedList: [TBreed]) {
        print(breedList.count)
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        print("BreedViewController")
    }
    
    override func initializeViewModel() -> BaseViewModel<BaseView> {
        return BreedViewModel(view: self).getViewModel()
    }
}
