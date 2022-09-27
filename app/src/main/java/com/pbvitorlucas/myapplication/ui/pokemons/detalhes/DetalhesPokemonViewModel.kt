package com.pbvitorlucas.myapplication.ui.pokemons.detalhes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbvitorlucas.myapplication.dao.PokemonDao
import com.pbvitorlucas.myapplication.model.CreatedPokemon

class DetalhesPokemonViewModel : ViewModel() {

    val pokemon = MutableLiveData<CreatedPokemon>()
    val msg = MutableLiveData<String>()

    fun retornaPokemon(pokemonId: Int) {
        PokemonDao.retornaPokemon(pokemonId).addOnSuccessListener {
            pokemon.value = it.toObjects(CreatedPokemon::class.java).first()
        }.addOnFailureListener {
            msg.value = it.message
        }
    }

    fun excluirPokemon() {
        PokemonDao.deletaPokemon(pokemon.value!!.idFirebase!!)
    }
}