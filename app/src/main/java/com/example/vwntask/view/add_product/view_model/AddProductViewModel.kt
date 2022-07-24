package com.example.vwntask.view.add_product.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vwntask.model.database.RoomDAO
import com.example.vwntask.model.pojo.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddProductViewModel(private val roomDAO: RoomDAO) : ViewModel() {

    fun insertProduct(product: Product){
        viewModelScope.launch (Dispatchers.IO) {
            roomDAO.insertProduct(product)
           var l =  roomDAO.getAllProducts()
            Log.e("TAG", "getAllProducts:  ${l.size}", )
        }
    }
}