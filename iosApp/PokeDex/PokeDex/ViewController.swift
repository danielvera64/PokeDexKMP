//
//  ViewController.swift
//  PokeDex
//
//  Created by Daniel Vera on 11/18/19.
//  Copyright © 2019 zakumi. All rights reserved.
//

import UIKit
import Shared

class ViewController: UIViewController {

  let viewModel = PokeListViewModel(api: PokeApi())
  
  override func viewDidLoad() {
    super.viewDidLoad()
    // Do any additional setup after loading the view.
    viewModel.on(listUpdated: { list in
      print("---------------\nPOKELIST:\n\(list)\n---------------\n")
    })
    viewModel.getList()
  }


}

