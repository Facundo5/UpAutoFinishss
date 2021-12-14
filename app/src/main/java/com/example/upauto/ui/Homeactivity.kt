package com.example.upauto.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upauto.*
import com.example.upauto.R
import com.example.upauto.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item.*

class Homeactivity : AppCompatActivity(), MyAdapter.OnCarsClickListener {
    private lateinit var adapter: MyAdapter
    private lateinit var Auth: FirebaseAuth
    private val viewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Auth = FirebaseAuth.getInstance()
        BtnSell.setOnClickListener {
            Carssell()
        }
        Btnlogout.setOnClickListener {
            onLogoutclick()
        }
        adapter = MyAdapter(context = this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        observeData()


    }

    fun observeData() {
        shimmer_view_container.startShimmer()
        viewModel.fetchCarData().observe(this, Observer {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })

    }

    fun Carssell() {
        startActivity(Intent(this, sellactivity::class.java))
    }

    override fun onImageClick(imageUrl: String, titulo: String, descripcion: String, precio: String) {
        val intent = Intent(this, publicationsViewActivity::class.java)
        intent.putExtra("imageUrl", imageUrl)
        intent.putExtra("titulo", titulo)
        intent.putExtra("descripcion", descripcion)
        intent.putExtra("precio", precio)
        startActivity(intent)

    }

    override fun onItemClick(titulo: Unit) {

    }


    fun onLogoutclick() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, authactivity::class.java))

    }

}



