package com.pbvitorlucas.myapplication.ui.pokemons.userPokemons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbvitorlucas.myapplication.dao.AuthDao
import com.pbvitorlucas.myapplication.dao.PokemonDao
import com.pbvitorlucas.myapplication.model.CreatedPokemon

class UserPokemonsViewModel : ViewModel() {

    var pokemonsList = MutableLiveData<MutableList<CreatedPokemon>>()
    val msg = MutableLiveData<String>()

    fun retornaPokemonsUser() {
        val idUsuario = AuthDao.getCurrentUser()!!.uid

        PokemonDao.pokemonsPorId(idUsuario).addOnSuccessListener {
            pokemonsList.value = it.toObjects(CreatedPokemon::class.java)
        }.addOnFailureListener {
            msg.value = it.message
        }
    }
}