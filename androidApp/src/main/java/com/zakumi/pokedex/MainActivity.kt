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

class MainActivity : AppCompatActivity(), MembersView {

    private val repository by lazy { (application as MainApplication).dataRepository }
    private val presenter by lazy { MembersPresenter(this, repository = repository) }

    private val viewModel by lazy { PokeListViewModel(PokeApi()) }

    private lateinit var adapter: MemberAdapter

    override var isUpdating: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        greetingTextView.text = Greeting().greeting()
        setUpRecyclerView()
        presenter.onCreate()
        viewModel.onCreate()
        GlobalScope.launch(Dispatchers.Main) {
            viewModel
                .pokeListChannel
                .consumeAsFlow()
                .onEach { list ->
                    Log.d("POKELIST", list.toString())
                }
                .collect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        viewModel.onDestroy()
    }

    override fun onUpdate(members: List<Member>) {
        adapter.members = members
        runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    override fun showError(error: Throwable) {
        val errorMessage = when (error) {
            is UpdateProblem -> getString(R.string.update_internet_connection)
            else -> getString(R.string.unknow_error)
        }
        runOnUiThread {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpRecyclerView() {
        membersRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MemberAdapter(listOf())
        membersRecyclerView.adapter = adapter
    }

}
