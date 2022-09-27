package com.pbvitorlucas.myapplication.ui.editar

/*
class EditarViewModel (val campeaoDao: CampeaoDao) : ViewModel() {

    val status = MutableLiveData<Boolean>()
    var campeao = MutableLiveData<Campeao>()

    init {
        status.value = false
    }

    fun detalhesCampeao (idCampeao : Long) {
        viewModelScope.launch(Dispatchers.Default) {
            campeao.postValue(campeaoDao.exibir(idCampeao))
        }
    }

    fun editarCampeao (nomeCampeao: String, email: String, senha: String, idCampeao: Long,)  {

        val campeao = Campeao(nomeCampeao, email, senha, idCampeao)

        viewModelScope.launch(Dispatchers.Default) {
            campeaoDao.atualizar(campeao)
            status.postValue(true)
        }
    }
}*/