//
//  PostCell.swift
//  KMMTApp
//
//  Created by Jitty on 30/04/2021.
//

import UIKit
import shared

class ShowCell: UITableViewCell {
    
    var tvShow: TVShowInfo?
    
    @IBOutlet weak var showName: UILabel!
    @IBOutlet weak var showLanguage: UILabel!
    @IBOutlet weak var showSummary: UILabel!
    
    func bindData(_ post: TVShowInfo) {
        self.tvShow = post
        showSummary.text = post.summary
        showName.text = post.name
        showLanguage.text = post.language
        showLanguage.font = showLanguage.font.italic
    }
}
