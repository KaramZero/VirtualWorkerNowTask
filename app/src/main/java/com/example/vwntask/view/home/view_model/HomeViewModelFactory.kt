package com.example.vwntask.view.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vwntask.model.database.RoomDAO

class HomeViewModelFactory(private val roomDAO: RoomDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(roomDAO) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}