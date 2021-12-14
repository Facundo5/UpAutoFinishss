package com.example.upauto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.upauto.databinding.ActivityPublicationsviewBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_publicationsview.*

class publicationsViewActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityPublicationsviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicationsviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null){
            val objetoIntent: Intent = intent
            Glide.with(this).load(intent.
            getStringExtra("imageUrl")).into(auto_foto_view)
            val titulo = objetoIntent.getStringExtra("titulo")
            val descripcion = objetoIntent.getStringExtra("descripcion")
            val precio = objetoIntent.getStringExtra("precio")
            publicationsPriceC.text = precio
            publicationstitulo.text = titulo
            publicationsdescripcionB.text = descripcion
        }

    }
}