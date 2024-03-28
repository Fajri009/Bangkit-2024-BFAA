package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data

// Untuk menyimpan status pengambilan data
sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val error: String): Result<Nothing>()
    object Loading: Result<Nothing>()
}