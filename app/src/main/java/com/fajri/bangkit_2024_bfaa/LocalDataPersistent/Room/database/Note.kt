package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// bisa dijadikan sebuah tabel dalam Room
@Entity
@Parcelize
data class Note(
    // autoGenerate digunakan untuk membuat id secara otomatis
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable