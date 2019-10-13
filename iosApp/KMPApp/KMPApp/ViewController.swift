//
//  ViewController.swift
//  KMPApp
//
//  Created by Daniel Vera on 10/11/19.
//  Copyright © 2019 zakumi. All rights reserved.
//

import UIKit
import Shared

class ViewController: UIViewController, MembersView {
  
  @IBOutlet weak var greetingLabel: UILabel!
  @IBOutlet weak var membersTableView: UITableView!
  
  let memberList = MemberList()
  
  var isUpdating: Bool = false
  
  lazy var presenter: MembersPresenter = {
    MembersPresenter(view: self, repository: AppDelegate.appDelegate.dataRepository)
  }()
  
  override func viewDidLoad() {
    super.viewDidLoad()
    
    greetingLabel.text = Greeting().greeting()
    
    membersTableView.dataSource = self
    membersTableView.register(UINib(nibName: "MemberTableViewCell", bundle: nil),
                              forCellReuseIdentifier: "cell")
    membersTableView.rowHeight = 80
  }

  override func viewWillAppear(_ animated: Bool) {
    presenter.onCreate()
  }
  
  override func viewWillDisappear(_ animated: Bool) {
    presenter.onDestroy()
  }
  
  func onUpdate(members: [Member]) {
    memberList.updateMembers(members)
    membersTableView.reloadData()
  }
  
  func showError(error: KotlinThrowable) {
    print("Error: \(error.message ?? "")")
  }
  
}

extension ViewController: UITableViewDataSource {
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return memberList.members.count
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! MemberTableViewCell
    let member = memberList.members[indexPath.row]
    cell.memberLogin.text = member.login
    cell.memberAvatar.load(url: URL(string: member.avatarUrl)!)
    return cell
  }
  
}

extension UIImageView {
  
  func load(url: URL) {
    DispatchQueue.global().async { [weak self] in
      guard let data = try? Data(contentsOf: url),
        let image = UIImage(data: data) else
      {
        return
      }
      DispatchQueue.main.async {
        self?.image = image
      }
    }
  }
  
}

