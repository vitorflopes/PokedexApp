package com.pbvitorlucas.myapplication.dao

import com.pbvitorlucas.myapplication.model.Campeao
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CampeaoDao {

    companion object {
        private val collection = Firebase.firestore.collection("Campeoes")

        fun salvarCampeao(idCampeao: String, campeao: Campeao) {
            collection.document(idCampeao).set(campeao)
        }

        fun exibirCampeao(idCampeao: String): Task<QuerySnapshot> {
            return collection.whereEqualTo("id", idCampeao).get()
        }

        fun listarCampeoes(): CollectionReference {
            return collection
        }
    }
}