package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.database.Note
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.repository.NoteRepository


// sebagai penghubung antara Activity dengan Repository
class NoteAddUpdateViewModel(application: Application): ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note)  {
        mNoteRepository.delete(note)
    }
}