package com.example.vwntask.view.add_product.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vwntask.model.database.RoomDAO
import com.example.vwntask.model.pojo.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProductViewModel(private val roomDAO: RoomDAO) : ViewModel() {

    private val _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean> = _finish

    fun insertProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            roomDAO.insertProduct(product)
            withContext(Dispatchers.Main) {
                _finish.value = true
            }
        }
    }
}