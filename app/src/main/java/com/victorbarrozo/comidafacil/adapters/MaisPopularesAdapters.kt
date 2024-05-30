package com.victorbarrozo.comidafacil.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.databinding.PopularItemBinding
import com.victorbarrozo.comidafacil.pojo.CategoriaRefeicoes

class MaisPopularesAdapters(): RecyclerView.Adapter<MaisPopularesAdapters.ComidasPopularesViewHolder>() {
    lateinit var onItemClick: ((CategoriaRefeicoes)-> Unit)
    lateinit var onLongItemClick: ((CategoriaRefeicoes)-> Unit)
    private var listaRefeicoes= ArrayList<CategoriaRefeicoes>()
    fun setRefeicao(listaRefeicoes: ArrayList<CategoriaRefeicoes>){
        this.listaRefeicoes = listaRefeicoes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidasPopularesViewHolder {
        return  ComidasPopularesViewHolder(
            PopularItemBinding.inflate( LayoutInflater.from(parent.context),parent,false ) )
    }

    override fun onBindViewHolder(holder: ComidasPopularesViewHolder, position: Int) {
        Glide.with(  holder.itemView )
            .load( listaRefeicoes[position].strMealThumb )
            .into( holder.binding.imgPopularItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke( listaRefeicoes[position] )
        }
        holder.itemView.setOnClickListener {
            onLongItemClick.invoke( listaRefeicoes[position] )
            true
        }
    }
    override fun getItemCount(): Int {
        return listaRefeicoes.size

    }
    class ComidasPopularesViewHolder(val binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root)

}