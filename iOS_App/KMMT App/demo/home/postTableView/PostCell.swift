//
//  PostCell.swift
//  KMMTApp
//
//  Created by Innovateq on 30/04/2021.
//

import UIKit
import shared

class PostCell: UITableViewCell {
    
    var post: PostModel?
    
    @IBOutlet weak var postName: UILabel!
    @IBOutlet weak var postEmail: UILabel!
    @IBOutlet weak var postDesLbl: UILabel!
    
    func bindData(_ post: PostModel) {
        self.post = post
        postDesLbl.text = post.body
        postName.text = post.name
        postEmail.text = post.email
        postEmail.font = postEmail.font.italic
    }
}
