package com.example.vwntask.model.database

import androidx.room.*
import com.example.vwntask.model.pojo.Product

@Dao
interface RoomDAO {
    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("Delete FROM products")
    fun deleteAllProducts()
}