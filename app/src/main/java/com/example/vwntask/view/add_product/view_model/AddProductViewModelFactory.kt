package com.example.vwntask.view.add_product.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vwntask.model.database.RoomDAO

class AddProductViewModelFactory (private val roomDAO: RoomDAO): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
            AddProductViewModel(roomDAO) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}