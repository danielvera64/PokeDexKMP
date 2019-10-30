package com.zakumi.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zakumi.Greeting
import com.zakumi.api.PokeApi
import com.zakumi.api.UpdateProblem
import com.zakumi.model.Member
import com.zakumi.presentation.MembersPresenter
import com.zakumi.presentation.MembersView
import com.zakumi.viewModel.PokeListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { PokeListViewModel(PokeApi()) }

    private lateinit var adapter: PokemonListAdapter

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        greetingTextView.text = Greeting().greeting()
        setUpRecyclerView()
        viewModel.getList()
        bindViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun setUpRecyclerView() {
        membersRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonListAdapter(listOf(), WeakReference(viewModel))
        membersRecyclerView.adapter = adapter
    }

    private fun bindViewModel() {
        GlobalScope.launch {
            viewModel
                .pokeListChannel
                .consumeAsFlow()
                .onEach { list ->
                    Log.d("POKELIST", list.toString())
                    adapter.list = list
                    runOnUiThread { adapter.notifyDataSetChanged() }
                }
                .collect()
        }

        GlobalScope.launch {
            viewModel
                .errorChannel
                .consumeAsFlow()
                .onEach { error ->
                    Log.e(TAG, ": $error")
                }
                .collect()
        }
    }

}
