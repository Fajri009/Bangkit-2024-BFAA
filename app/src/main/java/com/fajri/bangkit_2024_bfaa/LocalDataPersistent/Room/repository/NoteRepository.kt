package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.database.*
import java.util.concurrent.*

// Kelas ini berfungsi sebagai penghubung antara ViewModel dengan database atau resource data
class NoteRepository(application: Application) {
    private val mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()

    // insert, delete, dan update diharuskan menggunakan executorService karena proses tersebut menggunakan thread yang berbeda yakni background thread
    fun insert(note: Note) {
        executorService.execute {
            mNotesDao.insert(note)
        }
    }

    fun delete(note: Note) {
        executorService.execute {
            mNotesDao.delete(note)
        }
    }

    fun update(note: Note) {
        executorService.execute {
            mNotesDao.update(note)
        }
    }
}