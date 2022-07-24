package com.example.vwntask.view.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vwntask.databinding.ItemProductBinding
import com.example.vwntask.databinding.ItemProductImageBinding
import com.example.vwntask.model.pojo.Product
import com.example.vwntask.view.add_product.view.adapter.ProductImagesAdapter
import com.example.vwntask.view.add_product.view.adapter.ProductsCommunicator

class HomeAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var productsList: ArrayList<Product> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val currentItem = productsList[position]
        holder.binding.apply {
            productNameTextView.text = currentItem.name
            productPriceTextView.text = currentItem.price.toString()
            Glide.with(context)
                .asBitmap()
                .load(currentItem.productImages[0])
                .into(productImageView)
        }

    }

    override fun getItemCount(): Int {
        return productsList.count()
    }

    fun setData(productsList: ArrayList<Product>) {
        this.productsList = productsList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)


}
