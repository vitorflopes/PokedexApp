package com.pbvitorlucas.myapplication.ui.pokemons.lista

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbvitorlucas.myapplication.dao.AuthDao
import com.pbvitorlucas.myapplication.dao.PokemonDao
import com.pbvitorlucas.myapplication.model.apiModel.Pokemon

class PokemonsViewModel : ViewModel() {

    var pokemons = MutableLiveData<List<Pokemon>>()
    var msg = MutableLiveData<String>()

    init {
        val idCampeao = AuthDao.getCurrentUser()!!.uid

        PokemonDao.listarPokemonsCampeao(idCampeao).addOnSuccessListener {
            pokemons.value = it.toObjects(Pokemon::class.java)
        }.addOnFailureListener {
            msg.value = it.message
        }
    }
}