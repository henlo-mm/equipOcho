package com.appmovil.mediapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appmovil.mediapp.R
import com.appmovil.mediapp.databinding.ActivityRegisterBinding
import com.appmovil.mediapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        setup()
    }

    private fun setup() {
        binding.button.setOnClickListener {

            registerUser()

        }
    }
    private fun isInputValid(): Boolean {
        val email = binding.emailregister.text.toString()
        val pass = binding.editcontrasena.text.toString()
        val name = binding.editnombre.text.toString()
        val lastname = binding.editApellido.text.toString()

        if (name.isEmpty()) {
            binding.editnombre.error = "Nombre requerido"
            binding.editnombre.requestFocus()
            return false
        }

        if (lastname.isEmpty()) {
            binding.editApellido.error = "Apellido requerido"
            binding.editApellido.requestFocus()
            return false
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.d("wwewe", "sdds")
            binding.emailregister.error = "Correo electrónico inválido"
            binding.emailregister.requestFocus()
            return false
        }

        if (pass.isEmpty() || pass.length < 6) {
            binding.editcontrasena.error = "Contraseña debe tener al menos 6 caracteres"
            binding.editcontrasena.requestFocus()
            return false
        }

        return true
    }

    private fun registerUser() {
        val email = binding.editemailregister.text.toString().trim()
        val pass = binding.editpasswordregister.text.toString()
        val name = binding.editnombre.text.toString()
        val lastname = binding.editApellido.text.toString()

        authViewModel.registerUser(email, pass, name, lastname, "patient") { isRegister ->
            if (isRegister) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                goToHome(email)

            } else {
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToHome(email: String?){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }
}
