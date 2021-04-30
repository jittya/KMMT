//
//  PostTableViewDataSource.swift
//  KMMTApp
//
//  Created by Innovateq on 30/04/2021.
//

import UIKit
import shared

extension HomeViewController : UITableViewDataSource {

    
   func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       return postList.count
   }
   
   func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
       let cell = tableView.dequeueReusableCell(withIdentifier: "PostCell", for: indexPath)
       if let postCell = cell as? PostCell {
        let post = postList[indexPath.row]
        postCell.bindData(post)
       }
       return cell
   }

}
