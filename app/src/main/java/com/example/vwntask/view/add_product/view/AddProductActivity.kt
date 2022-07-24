package com.example.vwntask.view.add_product.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.example.vwntask.R
import com.example.vwntask.databinding.ActivityAddProductBinding
import com.example.vwntask.model.database.RoomDb
import com.example.vwntask.model.pojo.Product
import com.example.vwntask.view.add_product.view.adapter.ProductImagesAdapter
import com.example.vwntask.view.add_product.view.adapter.ProductsCommunicator
import com.example.vwntask.view.add_product.view_model.AddProductViewModel
import com.example.vwntask.view.add_product.view_model.AddProductViewModelFactory
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddProductActivity : AppCompatActivity(), ProductsCommunicator {

    private lateinit var viewModel: AddProductViewModel
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var productImagesAdapter: ProductImagesAdapter
    private val newProduct = Product()
    private val mealList = arrayListOf("Breakfast", "Dinner", "Desserts")
    private val typeList = arrayListOf("Plates", "Hot Drinks", "Iced Coffee")
    private var mealIndex = 0
    private var typeIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database: RoomDb = RoomDb.getInstance(applicationContext)
        val factory = AddProductViewModelFactory(database.roomDAO())
        viewModel = ViewModelProvider(this, factory)[AddProductViewModel::class.java]

        productImagesAdapter = ProductImagesAdapter(this, this)

        binding.mealEditText.setText(mealList[mealIndex])
        binding.itemTypeEditText.setText(typeList[typeIndex])

        initAdapter()
        initActions()

        viewModel.finish.observe(this) {
            if (it)
                finish()
        }

    }

    private fun initAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.HORIZONTAL

        binding.productImagesRecycleView.layoutManager = llm
        binding.productImagesRecycleView.adapter = productImagesAdapter

        productImagesAdapter.setData(newProduct.productImages)

    }

    private fun initActions() {
        binding.mealUpArrow.setOnClickListener {
            if (mealIndex > 0) {
                mealIndex--
                binding.mealEditText.setText(mealList[mealIndex])
                binding.mealDownArrow.setImageResource(R.drawable.down_arrow_active)
            }
            if (mealIndex == 0) {
                binding.mealUpArrow.setImageResource(R.drawable.up_arrow_inactive)
            }
        }
        binding.mealDownArrow.setOnClickListener {
            if (mealIndex < mealList.count() - 1) {
                mealIndex++
                binding.mealEditText.setText(mealList[mealIndex])
                binding.mealUpArrow.setImageResource(R.drawable.up_arrow_active)
            }
            if (mealIndex == mealList.count() - 1) {
                binding.mealDownArrow.setImageResource(R.drawable.down_arrow_inactive)
            }
        }

        binding.itemTypeUpArrow.setOnClickListener {
            if (typeIndex > 0) {
                typeIndex--
                binding.itemTypeEditText.setText(typeList[typeIndex])
                binding.itemTypeDownArrow.setImageResource(R.drawable.down_arrow_active)
            }
            if (typeIndex == 0) {
                binding.itemTypeUpArrow.setImageResource(R.drawable.up_arrow_inactive)
            }
        }
        binding.itemTypeDownArrow.setOnClickListener {
            if (typeIndex < typeList.count() - 1) {
                typeIndex++
                binding.itemTypeEditText.setText(typeList[typeIndex])
                binding.itemTypeUpArrow.setImageResource(R.drawable.up_arrow_active)
            }
            if (typeIndex == typeList.count() - 1) {
                binding.itemTypeDownArrow.setImageResource(R.drawable.down_arrow_inactive)
            }
        }

        binding.backImageView.setOnClickListener {
            finish()
        }
        binding.doneBTN.setOnClickListener {

            if (binding.productNameEditText.text.toString().isEmpty()) {
                binding.productNameEditText.error = "invalid name"
                return@setOnClickListener
            }
            if (binding.productInfoEditText.text.toString().isEmpty()) {
                binding.productInfoEditText.error = "invalid info"
                return@setOnClickListener
            }
            if (binding.priceEditText.text.toString().isEmpty()) {
                binding.priceEditText.error = "invalid price"
                return@setOnClickListener
            }

            binding.doneBTN.isEnabled = false
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
                newProduct.productImages.add(byteArray)
                productImagesAdapter.setData(newProduct.productImages)
                binding.productImagesRecycleView.adapter = productImagesAdapter
                TransitionManager.beginDelayedTransition(binding.productImagesRecycleView, Fade())

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