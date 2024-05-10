package com.victorbarrozo.comidafacil.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.databinding.ItemRefeicaoBinding
import com.victorbarrozo.comidafacil.pojo.CategoriaRefeicoes

class CategoriaRefeicoesAdapter: RecyclerView.Adapter< CategoriaRefeicoesAdapter.CategoriaRefeicoesViewModel>() {
    var listaRefeicoes = ArrayList<CategoriaRefeicoes>()

    fun setListaRefeicoes(listaRefeicoes: List<CategoriaRefeicoes>) {
       this.listaRefeicoes = listaRefeicoes as ArrayList<CategoriaRefeicoes>
        notifyDataSetChanged()

    }
    inner class CategoriaRefeicoesViewModel(val binding:ItemRefeicaoBinding ): RecyclerView.ViewHolder( binding.root )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaRefeicoesViewModel {
        return CategoriaRefeicoesViewModel(
            ItemRefeicaoBinding.inflate( LayoutInflater.from(parent.context))
        )
    }
    override fun onBindViewHolder(holder: CategoriaRefeicoesViewModel, position: Int) {
        Glide.with( holder.itemView )
            .load( listaRefeicoes[position].strMealThumb)
            .into( holder.binding.imgRefeicao )
        holder.binding.tvNomeRefeicao.text = listaRefeicoes[position].strMeal
    }
    override fun getItemCount(): Int {
        return listaRefeicoes.size
    }
}