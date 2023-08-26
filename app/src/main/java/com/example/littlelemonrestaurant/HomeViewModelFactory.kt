package com.example.littlelemonrestaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(appDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}