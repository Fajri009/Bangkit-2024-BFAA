package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SQLite.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null
): Parcelable