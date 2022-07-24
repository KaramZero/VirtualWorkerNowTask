package com.example.vwntask.view.home.view

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.vwntask.R
import com.example.vwntask.databinding.ActivityHomeBinding
import com.example.vwntask.model.database.RoomDb
import com.example.vwntask.model.pojo.Product
import com.example.vwntask.view.add_product.view.AddProductActivity
import com.example.vwntask.view.home.view_model.HomeViewModel
import com.example.vwntask.view.home.view_model.HomeViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private var productsList = ArrayList<Product>()
    private var meal: String = "Breakfast"
    private var type: String = "All"
    private var filteredProductsList = ArrayList<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database: RoomDb = RoomDb.getInstance(applicationContext)
        val factory = HomeViewModelFactory(database.roomDAO())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        initAdapter()
        initActions()

        viewModel.products.observe(this) {
            productsList = it
            showFilteredProducts()
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllProducts()
    }

    override fun onStop() {
        super.onStop()
        homeAdapter.setData(ArrayList())
        binding.productsRecycleView.adapter = homeAdapter
    }

    private fun initAdapter() {

        homeAdapter = HomeAdapter(this)

        val glm = GridLayoutManager(this, 2)
        glm.orientation = GridLayoutManager.VERTICAL

        binding.productsRecycleView.layoutManager = glm
        binding.productsRecycleView.adapter = homeAdapter

    }

    private fun initActions() {

        binding.breakfastTextView.setOnClickListener {
            binding.breakfastTextView.setBackgroundResource(R.drawable.sign_in_button_background)
            binding.dinnerTextView.setBackgroundResource(R.drawable.card_border)
            binding.dessertsTextView.setBackgroundResource(R.drawable.card_border)

            binding.breakfastTextView.setTextColor(getColor(R.color.gray))
            binding.dinnerTextView.setTextColor(getColor(R.color.black))
            binding.dessertsTextView.setTextColor(getColor(R.color.black))

            meal = "Breakfast"
            showFilteredProducts()
        }
        binding.dinnerTextView.setOnClickListener {
            binding.breakfastTextView.setBackgroundResource(R.drawable.card_border)
            binding.dinnerTextView.setBackgroundResource(R.drawable.sign_in_button_background)
            binding.dessertsTextView.setBackgroundResource(R.drawable.card_border)

            binding.breakfastTextView.setTextColor(getColor(R.color.black))
            binding.dinnerTextView.setTextColor(getColor(R.color.white))
            binding.dessertsTextView.setTextColor(getColor(R.color.black))

            meal = "Dinner"
            showFilteredProducts()
        }
        binding.dessertsTextView.setOnClickListener {
            binding.breakfastTextView.setBackgroundResource(R.drawable.card_border)
            binding.dinnerTextView.setBackgroundResource(R.drawable.card_border)
            binding.dessertsTextView.setBackgroundResource(R.drawable.sign_in_button_background)

            binding.breakfastTextView.setTextColor(getColor(R.color.black))
            binding.dinnerTextView.setTextColor(getColor(R.color.black))
            binding.dessertsTextView.setTextColor(getColor(R.color.white))

            meal = "Desserts"
            showFilteredProducts()
        }

        binding.allTextView.setOnClickListener {
            binding.allTextView.setTextColor(getColor(R.color.black))
            binding.platesTextView.setTextColor(getColor(R.color.gray))
            binding.hotDrinksTextView.setTextColor(getColor(R.color.gray))
            binding.icedCoffeeTextView.setTextColor(getColor(R.color.gray))
            type = "All"
            showFilteredProducts()
        }
        binding.platesTextView.setOnClickListener {
            binding.allTextView.setTextColor(getColor(R.color.gray))
            binding.platesTextView.setTextColor(getColor(R.color.black))
            binding.hotDrinksTextView.setTextColor(getColor(R.color.gray))
            binding.icedCoffeeTextView.setTextColor(getColor(R.color.gray))
            type = "Plates"
            showFilteredProducts()
        }
        binding.hotDrinksTextView.setOnClickListener {
            binding.allTextView.setTextColor(getColor(R.color.gray))
            binding.platesTextView.setTextColor(getColor(R.color.gray))
            binding.hotDrinksTextView.setTextColor(getColor(R.color.black))
            binding.icedCoffeeTextView.setTextColor(getColor(R.color.gray))
            type = "Hot Drinks"
            showFilteredProducts()
        }
        binding.icedCoffeeTextView.setOnClickListener {
            binding.allTextView.setTextColor(getColor(R.color.gray))
            binding.platesTextView.setTextColor(getColor(R.color.gray))
            binding.hotDrinksTextView.setTextColor(getColor(R.color.gray))
            binding.icedCoffeeTextView.setTextColor(getColor(R.color.black))
            type = "Iced Coffee"
            showFilteredProducts()
        }

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
    }

    override fun onBackPressed() {
        showDialog()
    }

    private fun showDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirmation")
        alertDialog.setMessage("How do you want to exit?")
        alertDialog
            .setCancelable(true)
            .setPositiveButton("Sign out") { _, _ ->
                val sharedPreferences: SharedPreferences = getSharedPreferences("userData", 0)
                sharedPreferences.edit().putBoolean("loggedIn", false).apply()
                finish()
            }
            .setNegativeButton(
                "Keep me logged in"
            ) { _, _ ->
                finishAffinity()
            }

        alertDialog.show()
    }

    private fun showFilteredProducts() {
        filteredProductsList = ArrayList()

        productsList.forEach {
            if (it.meal == meal && (type == "All" || it.type == type))
                filteredProductsList.add(it)
        }

        if (filteredProductsList.isEmpty())
            binding.noItemLayout.visibility = View.VISIBLE
        else
            binding.noItemLayout.visibility = View.INVISIBLE

        homeAdapter.setData(filteredProductsList)
        binding.productsRecycleView.adapter = homeAdapter
        TransitionManager.beginDelayedTransition(binding.productsRecycleView, Slide())
    }

}