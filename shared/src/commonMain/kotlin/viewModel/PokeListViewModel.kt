package com.zakumi.viewModel

import com.zakumi.ApplicationDispatcher
import com.zakumi.api.PokeApi
import com.zakumi.model.Pokemon
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class PokeListViewModel(
    private val api: PokeApi
):  CoroutinePresenter(ApplicationDispatcher) {

    val pokeListChannel = Channel<List<Pokemon>>()
    val errorChannel = Channel<Exception>()

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
            (cause as Exception)
                .let { errorChannel.send(it) }
            throw cause
        }
        pokeListChannel.send(newPokeList.results)
    }

}