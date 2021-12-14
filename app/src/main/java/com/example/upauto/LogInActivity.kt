package com.example.upauto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.example.upauto.databinding.ActivityLogInBinding
import com.example.upauto.ui.Homeactivity
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    //viewBinding
    private lateinit var binding: ActivityLogInBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()




        binding.NoAccountR.setOnClickListener {
            donthaveaccount()
        }
        binding.btnlogin.setOnClickListener {
            validateData()
            checkUser()
        }
    }
    fun donthaveaccount() {
        startActivity(Intent(this, authactivity::class.java))

    }
    fun validateData() {
        //get data
        email = binding.editTextemailLogin.text.toString().trim()
        password = binding.editTextPassworwLogin.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextemailLogin.error = "Este no es un correo"
        } else if (TextUtils.isEmpty(password)) {
            // no password enter
            binding.editTextPassworwLogin.error = "Porfavor ingrese una contraseña"
        } else {
            //data is validated, begin login
            firebaseLogin()
        }
    }
    fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //succes logged
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
            }
            .addOnFailureListener {
            }
    }
    private fun checkUser() {
        //el usuario entrara a la pantalla principal si su usuario esta creado
        //busca el usuario actual
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //el usuario ya estara logeado
            startActivity(Intent(this, Homeactivity::class.java))
        }
    }
}

