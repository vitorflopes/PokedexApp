package com.pbvitorlucas.myapplication.ui.perfil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbvitorlucas.myapplication.dao.AuthDao
import com.pbvitorlucas.myapplication.dao.CampeaoDao
import com.pbvitorlucas.myapplication.dao.PokemonDao
import com.pbvitorlucas.myapplication.model.Campeao
import com.google.firebase.auth.FirebaseUser

class PerfilViewModel : ViewModel() {

    val msg = MutableLiveData<String>()
    var campeao = MutableLiveData<Campeao>()
    val numPokemonsUser = MutableLiveData<Int>()

    fun detalhesCampeao () {

        val idCampeao = AuthDao.getCurrentUser()!!.uid

        CampeaoDao.exibirCampeao(idCampeao).addOnSuccessListener {
            campeao.value = it.toObjects(Campeao::class.java).first()
        }.addOnFailureListener {
                msg.value = it.message
        }
    }

    fun numPokemons() {
        val idUsuario = AuthDao.getCurrentUser()!!.uid

        PokemonDao.pokemonsPorId(idUsuario).addOnSuccessListener {
            numPokemonsUser.value = it.size()
        }.addOnFailureListener {
            msg.value = it.message
        }
    }

    fun retornaUsuarioLogado(): FirebaseUser? {
        return AuthDao.getCurrentUser()
    }
}