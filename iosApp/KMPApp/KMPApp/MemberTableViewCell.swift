//
//  MemberTableViewCell.swift
//  KMPApp
//
//  Created by Daniel Vera on 10/12/19.
//  Copyright © 2019 zakumi. All rights reserved.
//

import Foundation
import UIKit

class MemberTableViewCell: UITableViewCell {
  
  @IBOutlet weak var memberAvatar: UIImageView!
  @IBOutlet weak var memberLogin: UILabel!
  
  override func prepareForReuse() {
    memberAvatar.image = nil
  }
  
}
