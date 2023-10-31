package com.example.network.pokemons

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.api.PokemonApi
import com.example.network.common.MapResultError.safeApiCall
import com.example.network.common.NetworkResult
import com.example.network.common.Utilities
import com.example.network.pokemons.model.response.ListPokemonResponse
import com.example.network.pokemons.model.response.PokemonDetailResponse
import com.example.network.pokemons.model.response.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonListServiceImp @Inject constructor (private val pokemonApi: PokemonApi) : PagingSource<Int, Result>(), PokemonListService {

    override fun getAll() : Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = Utilities.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { this }
        ).flow
    }

    override suspend fun getPokemonByName(name: String): NetworkResult<PokemonDetailResponse> =
        safeApiCall{
            pokemonApi.getPokemonByName(name)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Result> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize
            val response:ListPokemonResponse = pokemonApi.getAll(limit, offset)
            val nextOffset = offset + limit

            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = nextOffset
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}