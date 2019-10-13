package com.zakumi.presentation

import com.zakumi.model.Member

interface MembersView: BaseView {
    var isUpdating: Boolean
    fun onUpdate(members: List<Member>)
}