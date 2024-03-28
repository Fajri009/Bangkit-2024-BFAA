package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SQLite.helper

import android.database.Cursor
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SQLite.db.DatabaseContract
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SQLite.entity.Note

object MappingHelper {
    // fungsi untuk mengonversi dari Cursor ke Arraylist
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Note> {
        val noteList = ArrayList<Note>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
                noteList.add(Note(id, title, description, date))
            }
        }
        return noteList
    }
}