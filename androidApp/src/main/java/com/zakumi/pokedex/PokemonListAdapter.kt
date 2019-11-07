package com.zakumi.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zakumi.model.PokeSprite
import com.zakumi.model.Pokemon
import com.zakumi.viewModel.PokeListViewModel
import kotlinx.android.synthetic.main.pokemon_list_view_holder.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class PokemonListAdapter (var list: List<Pokemon>,
                          private val viewModel: WeakReference<PokeListViewModel>
) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val pokeView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_list_view_holder, parent, false)
        return PokemonListViewHolder(pokeView, viewModel)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) = holder.bind(list[position])

    class PokemonListViewHolder(private val pokeView: View,
                                private val viewModel: WeakReference<PokeListViewModel>
    ) : RecyclerView.ViewHolder(pokeView) {

        fun bind(pokemon: Pokemon) {
            pokeView.nameTextView.text = pokemon.name.capitalize()
            pokeView.avatarImageView.setImageResource(R.drawable.ic_pokeball)

            val spriteChannel = Channel<PokeSprite?>()
            viewModel.get()?.getSpriteOf(pokemon.name.toLowerCase(), spriteChannel)

            GlobalScope.launch(Dispatchers.Main) {
                spriteChannel
                    .consumeAsFlow()
                    .take(1)
                    .onEach { sprite ->
                        sprite?.let {
                            Picasso.get().load(it.sprite).into(pokeView.avatarImageView)
                        }
                    }
                    .collect()
            }
        }
    }

}