package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Kelas ini berfungsi untuk mendapatkan waktu seperti tanggal, bulan, tahhun, dan jam
object DateHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}