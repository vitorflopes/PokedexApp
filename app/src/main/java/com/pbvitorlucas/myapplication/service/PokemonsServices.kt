package com.pbvitorlucas.myapplication.service

import com.pbvitorlucas.myapplication.model.PokemonsResponse
import com.pbvitorlucas.myapplication.model.PokemonsResponseName
import com.pbvitorlucas.myapplication.model.apiModel.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonsServices {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 30,
        @Query("offset") offset: Int = 0
    ) : PokemonsResponse

    @GET("pokemon/?limit=1155")
    suspend fun getPokemonsName() : PokemonsResponseName

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String) : Pokemon

    @GET("pokemon-species/{id}/")
    suspend fun getSpecies(@Path("id") id: String) : PokemonSpecies

    @GET("ability/{name}/")
    suspend fun getAbility(@Path("name") name: String) : Ability

    @GET("pokemon-form/{name}/")
    suspend fun getForm(@Path("name") name: String) : PokemonForm

    @GET("move/{name}/")
    suspend fun getMove(@Path("name") name: String) : Move

    @GET("stat/{name}/")
    suspend fun getStat(@Path("name") name: String) : Stat

    @GET("type/{name}/")
    suspend fun getType(@Path("name") name: String) : Type

    @GET("item/{name}/")
    suspend fun getItem (@Path("name") name: String) : Item

    @GET("nature")
    suspend fun getNatures () : PokemonNature

    @GET("nature/{name}/")
    suspend fun getNature(@Path("name") name: String) : Nature

    @GET("item?limit=1610")
    suspend fun getItens() : PokemonItem

    @GET("version-group?limit=30")
    suspend fun getGames() : PokemonGame
}