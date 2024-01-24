package com.example.contactify

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.contactify.adapter.UsersListAdapter
import com.example.contactify.viewModel.MainActivityViewModel


class MainActivity : AppCompatActivity(), LifecycleOwner, LifecycleObserver {
    lateinit var recyclerAdapter: UsersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initViewModel()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    private fun initRecyclerView() {
        /** Linear Listing*/
        /*val recyclerView = findViewById<RecyclerView>(R.id.rvListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = UsersListAdapter(this)
        recyclerView.adapter =recyclerAdapter*/


        /////
        val recyclerView = findViewById<RecyclerView>(R.id.rvListRecyclerView)
        recyclerAdapter = UsersListAdapter(this)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = recyclerAdapter

    }

    private fun initViewModel() {
        val viewModel: MainActivityViewModel =
            ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer {
            if (it != null) {
                it.data?.let { data ->
                    recyclerAdapter.setUsersList(data)
                    recyclerAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_LONG).show()
            }
        })

        //check connectivity
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (isConnected) {
            viewModel.makeAPICall() //API CAll
        } else {
            Toast.makeText(this, "Please Check your internet Connectivity", Toast.LENGTH_LONG)
                .show()
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun incrementSharedPrefCount() {
        val pref = applicationContext.getSharedPreferences(
            "ApplicationOpenCounter",
            0
        ) // 0 - for private mode

        val editor: SharedPreferences.Editor = pref.edit()
        val previousCounter = pref.getInt("counterValue", 0)
        if (previousCounter != 0) {
            val newCount = previousCounter + 1
            editor.putInt("counterValue", newCount)
            Toast.makeText(
                applicationContext,
                "Application Opened $newCount times",
                Toast.LENGTH_LONG
            ).show()
        } else {
            editor.putInt("counterValue", 1)
            Toast.makeText(
                applicationContext,
                "Application Opened the first time",
                Toast.LENGTH_LONG
            ).show()
        }
        editor.commit()
    }

}