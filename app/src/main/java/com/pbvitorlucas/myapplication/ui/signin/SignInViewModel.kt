package com.pbvitorlucas.myapplication.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbvitorlucas.myapplication.dao.AuthDao
import com.google.firebase.auth.FirebaseUser

class SignInViewModel : ViewModel() {

    val status = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    init {
        status.value = false
    }

    fun autenticar (email: String, senha: String) {

        val task = AuthDao.validarCampeao(email, senha)

        task.addOnSuccessListener {
            status.value = true
        }.addOnFailureListener {
            msg.value = it.message
        }
    }

    fun retornaUsuarioLogado(): FirebaseUser? {
        return AuthDao.getCurrentUser()
    }
}