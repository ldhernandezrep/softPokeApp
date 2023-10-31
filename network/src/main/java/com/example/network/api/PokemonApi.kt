package com.example.network.api

import com.example.network.pokemons.model.response.ListPokemonResponse
import com.example.network.pokemons.model.response.PokemonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getAll(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ):ListPokemonResponse


    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ):PokemonDetailResponse

}