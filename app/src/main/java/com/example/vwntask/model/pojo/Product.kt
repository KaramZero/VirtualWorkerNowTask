package com.example.vwntask.model.pojo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "NAME")
    @SerializedName("name") var name: String = "null",
    @ColumnInfo(name = "INFO")
    @SerializedName("info") var info: String? = null,
    @ColumnInfo(name = "MEAL")
    @SerializedName("meal") var meal: String? = null,
    @ColumnInfo(name = "TYPE")
    @SerializedName("type") var type: String? = null,
    @ColumnInfo(name = "PRICE")
    @SerializedName("price") var price: Double? = null,
    @ColumnInfo(name = "Images")
    @SerializedName("productImages") var productImages: ArrayList<ByteArray> = ArrayList(),

    )