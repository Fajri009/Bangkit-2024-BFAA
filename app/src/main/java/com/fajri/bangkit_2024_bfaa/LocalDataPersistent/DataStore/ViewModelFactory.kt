package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.DataStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainDataStoreViewModel::class.java)) {
            return MainDataStoreViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown viewModel class: " + modelClass.name)
    }
}