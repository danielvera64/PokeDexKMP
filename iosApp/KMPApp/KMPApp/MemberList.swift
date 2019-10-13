//
//  MemberList.swift
//  KMPApp
//
//  Created by Daniel Vera on 10/12/19.
//  Copyright © 2019 zakumi. All rights reserved.
//

import Foundation
import Shared

class MemberList {
  
  var members: [Member] = []
  
  func updateMembers(_ newMembers: [Member]) {
    members.removeAll()
    members.append(contentsOf: newMembers)
  }
  
}
