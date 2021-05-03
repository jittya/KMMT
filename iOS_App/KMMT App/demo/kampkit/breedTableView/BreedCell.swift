//
//  BreedCell.swift
//  KMMTApp
//
//  Created by Innovateq on 03/05/2021.
//


import UIKit
import shared

protocol BreedCellDelegate: class {
    func toggleFavorite(_ breed: TBreed)
}

class BreedCell: UITableViewCell {
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var favoriteButton: UIButton!
    var breed: TBreed?
    weak var delegate: BreedCellDelegate?
    
    func bindData(_ breed: TBreed) {
        self.breed = breed
        nameLabel.text = breed.name
        
        let imageName = (breed.favorite) ? "heart.fill" : "heart"
        favoriteButton.setImage(UIImage(systemName: imageName), for: .normal)
    }
    @IBAction func favoriteButtonPressed(_ sender: Any) {
        if let breed = breed {
            delegate?.toggleFavorite(breed)
        }
    }
}
