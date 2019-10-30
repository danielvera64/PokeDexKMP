package com.zakumi.pokedex

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zakumi.model.Pokemon
import com.zakumi.viewModel.PokeListViewModel
import kotlinx.android.synthetic.main.pokemon_list_view_holder.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class PokemonListAdapter (var list: List<Pokemon>,
                          val viewModel: WeakReference<PokeListViewModel>
) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val pokeView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_list_view_holder, parent, false)
        return PokemonListViewHolder(pokeView, viewModel)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) = holder.bind(list[position])

    class PokemonListViewHolder(val pokeView: View,
                                val viewModel: WeakReference<PokeListViewModel>
    ) : RecyclerView.ViewHolder(pokeView) {

//        init {
//            val pViewModel = viewModel.get()
//            if (pViewModel != null) {
//                GlobalScope.launch(Dispatchers.Main) {
//                    pViewModel
//                        .pokemonChannel
//                        .consumeAsFlow()
//                        .onEach { pokemon ->
//                            Log.d("POKESPRITE", pokemon.sprite)
//                            Picasso.get().load(pokemon.sprite).into(pokeView.avatarImageView)
//                        }
//                }
//            }
//
//        }

        fun bind(pokemon: Pokemon) {
            observeSprite()
            pokeView.nameTextView.text = pokemon.name.capitalize()
            viewModel.get()?.getSpriteOf(pokemon.name.toLowerCase())
        }

        private fun observeSprite() {
            GlobalScope.launch {
                viewModel.get()
                    ?.pokemonChannel
                    ?.consumeAsFlow()
                    ?.onEach { pokemon ->
                        Log.d("POKESPRITE", pokemon.sprite)
                    }
            }
        }

    }

}