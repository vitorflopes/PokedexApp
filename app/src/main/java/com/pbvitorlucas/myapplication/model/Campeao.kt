package com.pbvitorlucas.myapplication.model

class Campeao (
    val id: String? = null,
    var nomeCampeao: String? = null,
    var email: String? = null,
    var senha: String? = null) {

    fun strNome() : String {
        return "$nomeCampeao"
    }

    fun strEmail() : String {
        return "$email"
    }

    fun strSenha() : String {
        return "$senha"
    }
}