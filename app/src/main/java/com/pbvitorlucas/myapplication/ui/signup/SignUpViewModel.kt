package com.pbvitorlucas.myapplication.ui.signup

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbvitorlucas.myapplication.dao.AuthDao
import com.pbvitorlucas.myapplication.dao.CampeaoDao
import com.pbvitorlucas.myapplication.model.Campeao

class SignUpViewModel : ViewModel() {

    val status = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()
    var foto: Bitmap? = null

    init {
        status.value = false
    }

    fun pegarFoto(foto: Bitmap) {
        this.foto = foto
    }

    fun salvarCampeao(
        nomeCampeao: String, email: String, senha: String) {

        val task = AuthDao.cadastrarCampeao(email, senha)

        task.addOnSuccessListener {
            status.value = true
            val idCampeao = AuthDao.getCurrentUser()!!.uid
            val campeao = Campeao(idCampeao, nomeCampeao, email)
            CampeaoDao.salvarCampeao(idCampeao, campeao)
        }.addOnFailureListener {
            msg.value = it.message
        }


        /*
        viewModelScope
            .launch(Dispatchers.Default) {
            val campeaoId = campeaoDao.inserir(campeao)
            uploadFoto(campeaoId)
            status.postValue(true)
        }
        */
    }
}