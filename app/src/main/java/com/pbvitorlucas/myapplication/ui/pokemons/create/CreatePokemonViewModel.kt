package com.pbvitorlucas.myapplication.ui.pokemons.create

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbvitorlucas.myapplication.dao.PokemonDao
import com.pbvitorlucas.myapplication.model.CreatedPokemon
import com.pbvitorlucas.myapplication.model.apiModel.*
import com.pbvitorlucas.myapplication.service.RetroFit
import kotlinx.coroutines.launch

class CreatePokemonViewModel : ViewModel() {

    val status = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    var pokemon = MutableLiveData<Pokemon>()
    var item = MutableLiveData<Item>()
    var species = MutableLiveData<PokemonSpecies>()
    val game = MutableLiveData<VersionGroup>()

    var listItens = MutableLiveData<List<Item>>()
    var listAbilities = MutableLiveData<List<Ability>>()
    var listMoves = MutableLiveData<List<Move>>()
    var listStats = MutableLiveData<List<Stat>>()
    var listTypes = MutableLiveData<List<Type>>()
    var listNature = MutableLiveData<List<Nature>>()
    var listGames = MutableLiveData<List<VersionGroup>>()

    fun retornaPokemon(pokemonName: String, context: Context) {
        viewModelScope.launch {
            val helpListAbility = arrayListOf<Ability>()
            val helpListMove = arrayListOf<Move>()
            val helpListStat = arrayListOf<Stat>()
            val helpListType = arrayListOf<Type>()
            val helpListNature = arrayListOf<Nature>()

            pokemon.value = RetroFit.pokemonsService(context).getPokemon(pokemonName)
            species.value = RetroFit.pokemonsService(context).getSpecies(pokemon.value!!.species.name)
            listItens.value = RetroFit.pokemonsService(context).getItens().results
            listGames.value = RetroFit.pokemonsService(context).getGames().results

            for (ability in pokemon.value!!.abilities) {
                val ablt = RetroFit.pokemonsService(context).getAbility(ability.ability.name)
                helpListAbility.add(ablt)
            }
            listAbilities.value = helpListAbility

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

            for (nature in RetroFit.pokemonsService(context).getNatures().results) {
                val ntr = RetroFit.pokemonsService(context).getNature(nature.name)
                helpListNature.add(ntr)
            }
            listNature.value = helpListNature
        }
    }

    fun retornaItem (itemName: String, context: Context) {
        viewModelScope.launch {
            item.value = RetroFit.pokemonsService(context).getItem(itemName)
        }
    }

    fun inserirPokemon (pokemon: CreatedPokemon) {
        PokemonDao.inserirPokemon(pokemon).addOnSuccessListener {
            PokemonDao.setarIdfirebasePokemon(it.id)
            status.value = true
        }.addOnFailureListener {
            msg.value = it.message
        }
    }
}