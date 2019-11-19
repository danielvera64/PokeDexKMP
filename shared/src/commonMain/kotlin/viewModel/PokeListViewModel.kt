package com.zakumi.viewModel

import com.zakumi.ApplicationDispatcher
import com.zakumi.api.PokeApi
import com.zakumi.model.PokeSprite
import com.zakumi.model.Pokemon
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class PokeListViewModel(
    private val api: PokeApi
):  CoroutinePresenter(ApplicationDispatcher) {

    val pokeListChannel = Channel<List<Pokemon>>()
    val errorChannel = Channel<Exception>()

    override fun onCreate() {}

    fun getList() {
        launch {
            pokeListChannel.send(emptyList())
            updateList()
        }
    }

    fun getSpriteOf(name: String, spriteChannel: Channel<PokeSprite?>) {
        launch {
            getSprite(name, spriteChannel)
        }
    }

    fun on(listUpdated: (state: List<Pokemon>) -> Unit) {
        launch {
            pokeListChannel.consumeEach {
                listUpdated(it)
            }
        }
    }

    private suspend fun updateList() {
        val newPokeList = try {
            api.getList()
        } catch (cause: Throwable) {
            (cause as Exception)
                .let { errorChannel.send(it) }
            throw cause
        }
        pokeListChannel.send(newPokeList.results)
    }

    private suspend fun getSprite(name: String, spriteChannel: Channel<PokeSprite?>) {
        val pokemon = try {
            api.getSprite(name)
        } catch (cause: Throwable) {
            (cause as Exception)
                .let { errorChannel.send(it) }
            throw cause
        }
        spriteChannel.send(pokemon)
    }

}
