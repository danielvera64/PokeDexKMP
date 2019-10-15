package com.zakumi.pokedex

import android.app.Application
import com.zakumi.api.GitHubApi
import com.zakumi.repository.MembersDataRepository

class MainApplication : Application() {

    val dataRepository: com.zakumi.repository.DataRepository by lazy {
        MembersDataRepository(GitHubApi())
    }

}