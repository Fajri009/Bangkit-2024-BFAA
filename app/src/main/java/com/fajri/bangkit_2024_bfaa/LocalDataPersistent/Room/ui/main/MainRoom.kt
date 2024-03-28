package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.helper.ViewModelFactory
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.ui.insert.NoteAddUpdateActivity
import com.fajri.bangkit_2024_bfaa.R
import com.fajri.bangkit_2024_bfaa.databinding.ActivityMainRoomBinding

class MainRoom : AppCompatActivity() {
    private var _activityMainRoomBinding: ActivityMainRoomBinding? = null
    private val binding get() = _activityMainRoomBinding

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainRoomBinding = ActivityMainRoomBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewModel = obtainViewModel(this@MainRoom)
        viewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        adapter = NoteAdapter()

        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter

        binding?.fabAdd?.setOnClickListener {
            val intent = Intent(this@MainRoom, NoteAddUpdateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainRoomViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainRoomViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainRoomBinding = null
    }
}