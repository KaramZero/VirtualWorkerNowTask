package com.example.vwntask.view.add_product.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vwntask.databinding.ActivityAddProductBinding
import com.example.vwntask.model.database.RoomDb
import com.example.vwntask.model.pojo.Product
import com.example.vwntask.view.add_product.view.adapter.ProductImagesAdapter
import com.example.vwntask.view.add_product.view.adapter.ProductsCommunicator
import com.example.vwntask.view.add_product.view_model.AddProductViewModel
import com.example.vwntask.view.add_product.view_model.AddProductViewModelFactory
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddProductActivity : AppCompatActivity() , ProductsCommunicator{

    private lateinit var viewModel: AddProductViewModel
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var productImagesAdapter: ProductImagesAdapter
    private val newProduct = Product()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database: RoomDb = RoomDb.getInstance(applicationContext)
        val factory = AddProductViewModelFactory(database.roomDAO())
        viewModel = ViewModelProvider(this,factory)[AddProductViewModel::class.java]

        productImagesAdapter = ProductImagesAdapter(this,this)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.HORIZONTAL

        binding.productImagesRecycleView.layoutManager = llm
        binding.productImagesRecycleView.adapter = productImagesAdapter

        productImagesAdapter.setData(newProduct.productImages)

        binding.mealEditText.setText("new")
        binding.itemTypeEditText.setText("new")

        binding.doneBTN.setOnClickListener {
            newProduct.name = binding.productNameEditText.text.toString()
            newProduct.info = binding.productInfoEditText.text.toString()
            newProduct.meal = binding.mealEditText.text.toString()
            newProduct.type = binding.itemTypeEditText.text.toString()
            newProduct.price = binding.priceEditText.text.toString().toDouble()

            viewModel.insertProduct(newProduct)

        }

    }

    private fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        launchSomeActivity.launch(i)
    }

    private lateinit var selectedImageBitmap: Bitmap

    private var launchSomeActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode
            == RESULT_OK
        ) {
            val data = result.data
            // do your operation from here....
            if (data != null
                && data.data != null
            ) {
                val selectedImageUri = data.data

                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        selectedImageUri
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val byteArrayOutputStream = ByteArrayOutputStream()
                selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
                newProduct.productImages.add(byteArray)
                productImagesAdapter.setData(newProduct.productImages)
                binding.productImagesRecycleView.adapter = productImagesAdapter


            }
        }
    }


    override fun addNewProductImage() {
        imageChooser()
    }

    override fun deleteProductImage(byteArray: ByteArray) {
        newProduct.productImages.remove(byteArray)
        productImagesAdapter.setData(newProduct.productImages)
        binding.productImagesRecycleView.adapter = productImagesAdapter
    }

}