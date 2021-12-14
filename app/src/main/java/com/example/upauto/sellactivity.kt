package com.example.upauto

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.upauto.databinding.ActivitySellBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class sellactivity : AppCompatActivity() {

    lateinit var binding: ActivitySellBinding
    lateinit var imageUri: Uri
    private lateinit var db: FirebaseFirestore
    private lateinit var st: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)
        binding = ActivitySellBinding.inflate(layoutInflater)
        setContentView(binding.root)




        db = FirebaseFirestore.getInstance()

        binding.btnsubirimagen.setOnClickListener {
            selectimg()
        }
        binding.publicanouncer.setOnClickListener {
            uploadImage()
            savepublish()

        }
    }

    private fun savepublish() {
        db.collection("Cars").add(
            hashMapOf(
                "descripcion" to binding.EditTextDescripcioncar.text.toString(),
                "precio" to binding.EditextPrice.text.toString(),
                "marca" to binding.EditextMarcacar.text.toString(),
                "titulo" to binding.EditextTittlecar.text.toString(),
                "anio" to binding.editTextanio.text.toString(),
                "contacto" to binding.editTextcontact.text.toString(),
                "tel" to binding.editTexttel.text.toString()
            )
        )


    }

    private fun selectimg() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)

    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("publicando")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val formatter = SimpleDateFormat("yyyy_MM_dd_MM_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("Cars/$filename")
        storageReference.putFile(imageUri).addOnSuccessListener {

            binding.previewimage.setImageURI(imageUri)

            if (progressDialog.isShowing) progressDialog.dismiss()


        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.previewimage.setImageURI(imageUri)

        }
    }
}

