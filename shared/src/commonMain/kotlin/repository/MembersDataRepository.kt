package com.zakumi.repository

import com.zakumi.api.GitHubApi
import com.zakumi.api.UpdateProblem
import com.zakumi.model.Member

class MembersDataRepository(private val api: GitHubApi) : DataRepository {

    override var members: List<Member>? = null
    override var onRefreshListeners: List<() -> Unit> = emptyList()

    override suspend fun update() {
        val newMembers = try {
            api.getMembers()
        } catch (cause: Throwable) {
            throw  UpdateProblem()
        }
        if (newMembers != members) {
            members = newMembers
            callRefreshListeners()
        }
    }

    private fun callRefreshListeners() {
        onRefreshListeners.forEach { it() }
    }

}