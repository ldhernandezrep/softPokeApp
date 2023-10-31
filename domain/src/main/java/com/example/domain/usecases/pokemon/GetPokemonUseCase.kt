package com.example.domain.usecases.pokemon

import com.example.domain.usecases.pokemon.mapper.map
import com.example.domain.usecases.pokemon.mapper.toMapper
import com.example.domain.usecases.pokemon.model.ListPokemonModel
import com.example.domain.usecases.pokemon.model.PokemonDetail
import com.example.network.common.NetworkResult
import com.example.network.pokemons.PokemonListService
import java.lang.Exception
import javax.inject.Inject

interface IGetPokemonUseCase{
    suspend fun invoke(name:String):PokemonDetail
}

class GetPokemonUseCase @Inject constructor (private val pokemonNetwork: PokemonListService) : IGetPokemonUseCase {

        override suspend fun invoke(name:String): PokemonDetail {
            when (val response = pokemonNetwork.getPokemonByName(name)) {
                is NetworkResult.NetWorkSuccess -> {
                    return response.result.map()
                }

                is NetworkResult.NetworkFailure -> {
                    throw Exception()
                }
            }
        }



    }