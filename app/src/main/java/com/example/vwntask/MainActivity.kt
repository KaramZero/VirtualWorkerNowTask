package com.example.vwntask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vwntask.databinding.ActivityMainBinding
import com.example.vwntask.view.add_product.view.AddProductActivity
import com.example.vwntask.view.home.HomeActivity
import com.example.vwntask.view.sign_in.SignInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
    }
}