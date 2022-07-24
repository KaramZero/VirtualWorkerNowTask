package com.example.vwntask.view.add_product.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vwntask.databinding.ItemProductImageBinding
import kotlin.Int
import kotlin.collections.ArrayList

class ProductImagesAdapter(private val context: Context , private val communicator: ProductsCommunicator) :
    RecyclerView.Adapter<ProductImagesAdapter.ViewHolder>() {
    private var productsList: ArrayList<ByteArray> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position == productsList.count()){
            holder.binding.apply {
                deleteImageImageView.visibility = View.INVISIBLE
                root.setOnClickListener {
                    communicator.addNewProductImage()
                }
            }
        }else{
            val currentItem = productsList[position]
            holder.binding.apply {
                Glide.with(context)
                    .asBitmap()
                    .load(currentItem)
                    .into(productImageView)
                deleteImageImageView.setOnClickListener{
                    communicator.deleteProductImage(currentItem)
                }
            }

        }



    }

    override fun getItemCount(): Int {
        return productsList.count() + 1
    }

    fun setData(productsList: ArrayList<ByteArray>) {
        this.productsList = productsList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root)


}
