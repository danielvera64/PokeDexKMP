package com.zakumi.kmpapp

import android.app.Application
import com.zakumi.api.GitHubApi
import com.zakumi.model.MembersDataRepository
import com.zakumi.presentation.DataRepository

class MainApplication : Application() {

    val dataRepository: DataRepository by lazy {
        MembersDataRepository(GitHubApi())
    }

}