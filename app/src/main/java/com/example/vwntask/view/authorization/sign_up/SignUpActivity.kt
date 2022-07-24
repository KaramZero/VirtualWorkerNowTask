package com.example.vwntask.view.authorization.sign_up

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vwntask.databinding.ActivitySignUpBinding
import com.example.vwntask.view.authorization.sign_in.SignInActivity
import com.example.vwntask.view.home.view.HomeActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences: SharedPreferences = getSharedPreferences("userData", 0)

        binding.signInBTN.setOnClickListener {
            if (binding.userNameEditText.text.toString().isEmpty())
                binding.userNameEditText.error = "Invalid username"
            else if (binding.passwordEditText.text.toString().isEmpty())
                binding.passwordEditText.error = "Invalid password"
            else if (binding.passwordEditText.text.toString() != binding.retypePasswordEditText.text.toString())
                binding.retypePasswordEditText.error = "password not match"
            else {
                if (!sharedPreferences.contains(binding.userNameEditText.text.toString())
                ) {
                    sharedPreferences.edit().putBoolean("loggedIn", true).putString(
                        binding.userNameEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    ).apply()

                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, "username found", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "please sign in", Toast.LENGTH_SHORT).show()

                }
            }
        }

        binding.signInNowTextView.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}