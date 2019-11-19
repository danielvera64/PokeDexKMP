package com.zakumi.presentation

import com.zakumi.model.Pokemon

interface PokeListView {
    fun onUpdatePoke(list: List<Pokemon>)
}