package com.pbvitorlucas.myapplication.ui.pokemons.form

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AllPokemonsVMF(val context: Context):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllPokemonsViewModel(context) as T
    }
}