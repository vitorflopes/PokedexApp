package com.pbvitorlucas.myapplication.ui.pokemons.form

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbvitorlucas.myapplication.model.apiModel.*
import com.pbvitorlucas.myapplication.service.RetroFit
import kotlinx.coroutines.launch

class PokemonFormViewModel() : ViewModel() {

    var status = MutableLiveData<Boolean>()
    var msg = MutableLiveData<String>()

    var pokemon = MutableLiveData<Pokemon>()
    var species = MutableLiveData<PokemonSpecies>()
    var listAbilities = MutableLiveData<List<Ability>>()
    var listForms = MutableLiveData<List<PokemonForm>>()
    var listHeldItems = MutableLiveData<List<Item>>()
    var listMoves = MutableLiveData<List<Move>>()
    var listStats = MutableLiveData<List<Stat>>()
    var listTypes = MutableLiveData<List<Type>>()

    fun retornaPokemon(pokemonName: String, context: Context) {

        viewModelScope.launch {
            val helpListAbility = arrayListOf<Ability>()
            val helpListForm = arrayListOf<PokemonForm>()
            val helpListItem = arrayListOf<Item>()
            val helpListMove = arrayListOf<Move>()
            val helpListStat = arrayListOf<Stat>()
            val helpListType = arrayListOf<Type>()

            pokemon.value = RetroFit.pokemonsService(context).getPokemon(pokemonName)
            species.value = RetroFit.pokemonsService(context).getSpecies(pokemon.value!!.species.name)

            for (abilitie in pokemon.value!!.abilities) {
                val ablt = RetroFit.pokemonsService(context).getAbility(abilitie.ability.name)
                helpListAbility.add(ablt)
            }
            listAbilities.value = helpListAbility

            for (form in pokemon.value!!.forms) {
                val frm = RetroFit.pokemonsService(context).getForm(form.name)
                helpListForm.add(frm)
            }
            listForms.value = helpListForm

            for (item in pokemon.value!!.held_items) {
                val itm = RetroFit.pokemonsService(context).getItem(item.item.name)
                helpListItem.add(itm)
            }
            listHeldItems.value = helpListItem

            for (move in pokemon.value!!.moves) {
                val mv = RetroFit.pokemonsService(context).getMove(move.move.name)
                helpListMove.add(mv)
            }
            listMoves.value = helpListMove

            for (stat in pokemon.value!!.stats) {
                val stt = RetroFit.pokemonsService(context).getStat(stat.stat.name)
                helpListStat.add(stt)
            }
            listStats.value = helpListStat

            for (type in pokemon.value!!.types) {
                val tp = RetroFit.pokemonsService(context).getType(type.type.name)
                helpListType.add(tp)
            }
            listTypes.value = helpListType
        }
    }
}