package com.pbvitorlucas.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pbvitorlucas.myapplication.R
import com.pbvitorlucas.myapplication.model.apiModel.Pokemon
import com.squareup.picasso.Picasso

class AdapterPokemon(private val context: Context, private val pokemons: MutableList<Pokemon>)
    : RecyclerView.Adapter<AdapterPokemon.PokemonViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.pokemon_list, parent, false)
        val holder = PokemonViewHolder(itemLista, mListener)

        return holder
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        Picasso.get().load(pokemons[position].sprites.front_default).into(holder.imgFront)
        Picasso.get().load(pokemons[position].sprites.back_default).into(holder.imgBack)
        holder.name.text = pokemons[position].name
    }

    override fun getItemCount(): Int = pokemons.size

    inner class PokemonViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvNamePokemon)
        val imgFront = itemView.findViewById<ImageView>(R.id.ivFrontPoke)
        val imgBack = itemView.findViewById<ImageView>(R.id.ivBackPoke)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}