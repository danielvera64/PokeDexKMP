package com.zakumi.repository

import com.zakumi.model.Member

interface DataRepository {
    val members: List<Member>?
    var onRefreshListeners: List<() -> Unit>
    suspend fun update()
}