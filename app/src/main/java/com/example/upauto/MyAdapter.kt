package com.example.upauto
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item.view.*


class MyAdapter(private val context: Context,
                private val itemClickListener:OnCarsClickListener


): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var db: FirebaseFirestore
    private var dataList = mutableListOf<Car>()
    interface OnCarsClickListener{
        fun onImageClick(imageUrl: String, titulo: String, descripcion: String, precio: String)
        fun onItemClick(titulo: Unit)
    }
    fun setListData(data:MutableList<Car>){
        dataList = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val auto = dataList[position]
        holder.bindView(auto)

    }

    override fun getItemCount(): Int {
       return if(dataList.size > 0) {
            dataList.size
        }else{
            0
        }
    }
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindView(auto:Car){

            itemView.auto_title.setOnClickListener{ itemClickListener.onItemClick(removeItem(viewHolder = this)) }
            itemView.auto_foto.setOnClickListener{ itemClickListener.onImageClick(auto.imageUrl,auto.titulo,auto.descripcion,auto.precio)}
            Glide.with(context).load(auto.imageUrl).into(itemView.auto_foto)
            itemView.auto_title.text = auto.titulo
            itemView.auto_price.text = auto.precio
            itemView.auto_Descripcion.text = auto.descripcion
        }
    }
    fun removeItem(viewHolder: MyViewHolder) {
        dataList.removeAt(viewHolder.absoluteAdapterPosition)
        notifyItemRemoved(viewHolder.absoluteAdapterPosition)
    }
}



