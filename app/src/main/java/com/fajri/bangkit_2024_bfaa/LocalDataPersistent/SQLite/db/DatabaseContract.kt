package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SQLite.db

import android.provider.BaseColumns

// Kelas contract ini akan digunakan untuk mempermudah akses nama tabel dan nama kolom di dalam database kita
internal class DatabaseContract {
    internal class NoteColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
        }
    }
}