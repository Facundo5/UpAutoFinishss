package com.example.upauto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.example.upauto.databinding.ActivityAuthBinding
import com.example.upauto.ui.Homeactivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class authactivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)




        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.btnregister.setOnClickListener {
            validateData()
            savedata()

        }
        binding.HaveAccountR.setOnClickListener {
            haveaccount()
        }
    }

    private fun haveaccount() {
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }

    private fun savedata() {
        db.collection("users").document(email).set(
            hashMapOf(
                "email" to binding.editTextemailregister.text.toString(),
                "name" to binding.editTextname.toString(),
                "surname" to binding.editTextsurname.text.toString()
            )
        )
    }


    private fun validateData() {
        email = binding.editTextemailregister.text.toString().trim()
        password = binding.editTextPasswordregister.text.toString().trim()

        //validando datos
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //error email
            binding.editTextemailregister.error = "Correo invalido"
        } else if (TextUtils.isEmpty(password)) {
            // password no ingresada
            binding.editTextPasswordregister.error = "Porfavor ingresa una contraseña"

        } else if (password.length < 6) {
            //password corta
            binding.editTextPasswordregister.error = "Esta contraseña es muy corta"
        } else {
            // data valida puede continuar
            firebaseSignUp()


        }
    }

    private fun firebaseSignUp() {
        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //registro completo
                startActivity(Intent(this, Homeactivity::class.java))
                finish()
            }
            .addOnFailureListener {

            }

    }
}


