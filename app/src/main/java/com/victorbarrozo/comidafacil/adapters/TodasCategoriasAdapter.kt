package com.victorbarrozo.comidafacil.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.databinding.ItemCategoriaBinding
import com.victorbarrozo.comidafacil.pojo.Category
import com.victorbarrozo.comidafacil.pojo.ListaTodasCategorias

class TodasCategoriasAdapter(): RecyclerView.Adapter<TodasCategoriasAdapter.CategoriasViewHolder>(){

    private var listaTodasCategorias = ArrayList<Category>()
    var aoClicaricone : ((Category) -> Unit)? = null
    fun setListaTodasCategorias ( listaTodasCategorias: List< Category > ){
        this.listaTodasCategorias = listaTodasCategorias as ArrayList<Category>
        notifyDataSetChanged()
    }
    inner class CategoriasViewHolder(val binding: ItemCategoriaBinding):RecyclerView.ViewHolder( binding.root )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriasViewHolder {
        return CategoriasViewHolder(
            ItemCategoriaBinding.inflate( LayoutInflater.from(parent.context ))
        )
    }

    override fun onBindViewHolder(holder: CategoriasViewHolder, position: Int) {
        Glide.with( holder.itemView )
            .load( listaTodasCategorias[position].strCategoryThumb )
            .into( holder.binding.imgCategoria )
        holder.binding.tvCategoriaNome.text = listaTodasCategorias[position].strCategory

        holder.itemView.setOnClickListener {
            aoClicaricone!!.invoke( listaTodasCategorias[position] )
        }
    }
    override fun getItemCount(): Int {
       return listaTodasCategorias.size
    }

}