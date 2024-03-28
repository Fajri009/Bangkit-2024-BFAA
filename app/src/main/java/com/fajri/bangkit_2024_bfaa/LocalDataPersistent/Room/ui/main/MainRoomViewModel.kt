package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.database.Note
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.repository.NoteRepository

class MainRoomViewModel(application: Application): ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}