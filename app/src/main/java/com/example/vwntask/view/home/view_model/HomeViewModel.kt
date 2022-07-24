package com.example.vwntask.view.home.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vwntask.model.database.RoomDAO
import com.example.vwntask.model.pojo.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val roomDAO: RoomDAO) : ViewModel() {

    private val _products = MutableLiveData<ArrayList<Product>>()
    val products: LiveData<ArrayList<Product>> = _products

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = roomDAO.getAllProducts()
            withContext(Dispatchers.Main) {
                _products.value = ArrayList(res)
            }
        }
    }
}