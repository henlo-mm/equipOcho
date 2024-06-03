package com.appmovil.mediapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appmovil.mediapp.R
import com.appmovil.mediapp.databinding.ActivityLoginBinding
import com.appmovil.mediapp.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setup()
    }

    private fun setup() {
        binding.btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnIngresar.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser(){
        val email = binding.editemail.text.toString()
        val pass = binding.editpassword.text.toString()
        authViewModel.loginUser(email,pass){ isLogin ->
            if (isLogin) {
                goToHome(email)
            }else {
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToHome(email: String?){
            putExtra("email",email)
        }
        startActivity(intent)
        finish()
    }
}
