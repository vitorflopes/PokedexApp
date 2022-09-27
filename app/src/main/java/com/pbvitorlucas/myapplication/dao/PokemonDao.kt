package com.pbvitorlucas.myapplication.dao

import com.pbvitorlucas.myapplication.model.CreatedPokemon
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PokemonDao {
    companion object {
        val collection = Firebase.firestore.collection("Pokemons")

        fun listarPokemonsCampeao(idCampeao: String): Task<QuerySnapshot> {
            return Firebase.firestore.collection("Campeoes")
                .document(idCampeao).collection("Pokemons").get()
        }

        fun inserirPokemon(pokemon: CreatedPokemon): Task<DocumentReference> {
            return collection.add(pokemon)
        }

        fun setarIdfirebasePokemon(idFirebase: String): Task<Void> {
            return collection.document(idFirebase).update("idFirebase", idFirebase)
        }

        fun pokemonsPorId(idUsuario: String): Task<QuerySnapshot> {
            return collection
                .whereEqualTo("idUsuario", idUsuario).get()
        }

        fun retornaPokemon(pokemonId: Int): Task<QuerySnapshot> {
            return collection
                .whereEqualTo("id", pokemonId).get()
        }

        fun deletaPokemon(idPokemon: String): Task<Void> {
            return collection.document(idPokemon).delete()
        }
    }
}