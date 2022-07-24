package com.example.vwntask

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vwntask.databinding.ActivityMainBinding
import com.example.vwntask.view.add_product.view.AddProductActivity
import com.example.vwntask.view.authorization.sign_in.SignInActivity
import com.example.vwntask.view.authorization.sign_up.SignUpActivity
import com.example.vwntask.view.home.view.HomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences: SharedPreferences = getSharedPreferences("userData", 0)

        if (sharedPreferences.getBoolean("loggedIn", false)) {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}