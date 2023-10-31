package com.example.network.pokemons

import androidx.paging.PagingData
import com.example.network.common.NetworkResult
import com.example.network.pokemons.model.response.ListPokemonResponse
import com.example.network.pokemons.model.response.PokemonDetailResponse
import com.example.network.pokemons.model.response.Result
import kotlinx.coroutines.flow.Flow

interface PokemonListService {
    fun getAll(): Flow<PagingData<Result>>
    suspend fun getPokemonByName(name:String): NetworkResult<PokemonDetailResponse>
}