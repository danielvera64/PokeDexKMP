package com.zakumi.kmpapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zakumi.Greeting
import com.zakumi.api.UpdateProblem
import com.zakumi.model.Member
import com.zakumi.presentation.MembersPresenter
import com.zakumi.presentation.MembersView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MembersView {

    private val repository by lazy { (application as MainApplication).dataRepository }
    private val presenter by lazy { MembersPresenter(this, repository = repository) }

    private lateinit var adapter: MemberAdapter

    override var isUpdating: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        greetingTextView.text = Greeting().greeting()
        setUpRecyclerView()
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
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
