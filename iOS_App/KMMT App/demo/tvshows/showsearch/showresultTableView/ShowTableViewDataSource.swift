//
//  PostTableViewDataSource.swift
//  KMMTApp
//
//  Created by Jitty on 30/04/2021.
//

import UIKit
import shared

extension TVShowsViewController : UITableViewDataSource {

    
   func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       return showList.count
   }
   
   func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
       let cell = tableView.dequeueReusableCell(withIdentifier: "ShowCell", for: indexPath)
       if let showCell = cell as? ShowCell {
           let show = showList[indexPath.row]
        showCell.bindData(show)
       }
       return cell
   }

}
