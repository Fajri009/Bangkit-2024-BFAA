package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.ui.insert.NoteAddUpdateViewModel
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Room.ui.main.MainRoomViewModel

// Kelas ini berfungsi untuk menambahkan context ketika memanggil kelas ViewModel di dalam Activity
// Hal ini digunakan untuk inisialisasi database di dalam NoteRepository
class ViewModelFactory private constructor(private val mApplication: Application): ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    // Dengan menggunakan cara ini, kita bisa mengirimkan parameter context dengan nama mApplication ke kelas MainViewModel
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainRoomViewModel::class.java)) {
            return MainRoomViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
            return NoteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}