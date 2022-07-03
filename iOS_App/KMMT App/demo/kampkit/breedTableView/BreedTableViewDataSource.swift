//
//  BreedTableViewDataSource.swift
//  KMMTApp
//
//  Created by Innovateq on 03/05/2021.
//

import UIKit
import shared

extension BreedViewController : UITableViewDataSource,BreedCellDelegate{
    func toggleFavorite(_ breed: Breed) {
        if(invertBreedFavouriteState != nil)
        {
            invertBreedFavouriteState!(breed)
        }
    }
    
   func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       return breedList.count
   }
   
   func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
       let cell = tableView.dequeueReusableCell(withIdentifier: "BreedCell", for: indexPath)
       if let breedCell = cell as? BreedCell {
        let breed = breedList[indexPath.row]
        breedCell.bindData(breed)
        breedCell.delegate=self
       }
       return cell
   }

}

