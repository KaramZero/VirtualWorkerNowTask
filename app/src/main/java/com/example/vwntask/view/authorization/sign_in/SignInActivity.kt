package com.example.vwntask.view.authorization.sign_in

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vwntask.databinding.ActivitySignInBinding
import com.example.vwntask.view.authorization.sign_up.SignUpActivity
import com.example.vwntask.view.home.view.HomeActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInBTN.setOnClickListener {
            if (binding.userNameEditText.text.toString().isEmpty())
                binding.userNameEditText.error = "Invalid username"
            else if (binding.passwordEditText.text.toString().isEmpty())
                binding.passwordEditText.error = "Invalid password"
            else {
                val sharedPreferences: SharedPreferences = getSharedPreferences("userData", 0)
                if (sharedPreferences.getString(
                        binding.userNameEditText.text.toString(),
                        ""
                    ) == binding.passwordEditText.text.toString()
                ) {
                    sharedPreferences.edit().putBoolean("loggedIn", true).apply()
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, "user not found", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Register first", Toast.LENGTH_SHORT).show()

                }
            }
        }

        binding.registerNowTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}