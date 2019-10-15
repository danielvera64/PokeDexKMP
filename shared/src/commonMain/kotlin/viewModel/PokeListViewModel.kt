package com.zakumi.viewModel

import com.zakumi.ApplicationDispatcher
import com.zakumi.api.PokeApi
import com.zakumi.api.UpdateProblem
import com.zakumi.model.Pokemon
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class PokeListViewModel(
    private val api: PokeApi
):  CoroutinePresenter(ApplicationDispatcher) {

    val pokeListChannel = Channel<List<Pokemon>>()

    override fun onCreate() {
        launch {
            pokeListChannel.send(emptyList())
            update()
        }
    }

    private suspend fun update() {
        val newPokeList = try {
            api.getList()
        } catch (cause: Throwable) {
            throw UpdateProblem()
        }
        pokeListChannel.send(newPokeList.results)
    }

//    override fun showError(error: Throwable) {
//    }

}