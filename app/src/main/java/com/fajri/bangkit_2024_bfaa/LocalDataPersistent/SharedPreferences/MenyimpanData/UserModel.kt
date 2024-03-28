package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SharedPreferences.MenyimpanData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var name: String? = null,
    var email: String? = null,
    var age: Int = 0,
    var phoneNumber: String? = null,
    var isLove: Boolean = false
): Parcelable
