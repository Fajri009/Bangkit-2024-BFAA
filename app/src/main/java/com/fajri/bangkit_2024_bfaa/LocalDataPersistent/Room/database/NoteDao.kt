package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.database

import androidx.lifecycle.LiveData
import androidx.room.*

// Kelas ini nantinya digunakan untuk melakukan eksekusi quiring (CRUD)
@Dao
interface NoteDao {
    // OnConflictStrategy.IGNORE digunakan jika data yang dimasukkan sama, maka dibiarkan saja
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}