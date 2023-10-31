package com.example.domain.usecases.pokemon

import androidx.paging.PagingData
import androidx.paging.map
import com.example.domain.usecases.pokemon.mapper.toMapper
import com.example.domain.usecases.pokemon.mapper.toModel
import com.example.domain.usecases.pokemon.model.ListPokemonModel
import com.example.domain.usecases.pokemon.model.PokemonModel
import com.example.network.common.NetworkResult
import com.example.network.pokemons.PokemonListService
import com.example.network.pokemons.model.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface IGetAllPokemonUseCase{
    fun invoke(): Flow<PagingData<PokemonModel>>
}

class GetAllPokemonUseCase @Inject constructor (private val pokemonNetwork: PokemonListService) : IGetAllPokemonUseCase {

    override fun invoke(): Flow<PagingData<PokemonModel>> {
        return pokemonNetwork.getAll()
            .map { pagingDataResponse ->
                pagingDataResponse.map { result ->
                    result.toModel()
                }
            }
    }
}

