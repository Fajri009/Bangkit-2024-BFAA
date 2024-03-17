package com.fajri.bangkit_2024_bfaa.AndroidArchitectureComponent.ViewModel

import androidx.lifecycle.ViewModel

class ViewModel: ViewModel() {
    var result = 0

    fun calculate(width: String, height: String, length: String) {
        result = width.toInt() * height.toInt() * length.toInt()
    }
}