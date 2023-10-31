package com.example.pokedexapp.ui.listpokemon.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.domain.usecases.pokemon.IGetAllPokemonUseCase
import com.example.domain.usecases.pokemon.model.PokemonModel
import com.example.pokedexapp.ui.detailpokemon.DetailViewState
import com.example.pokedexapp.ui.listpokemon.ListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getAllPokemonUseCase: IGetAllPokemonUseCase) :
    ViewModel(), LifecycleObserver {

    private val _viewState = MutableLiveData<ListViewState>()
    fun getViewState() = _viewState

    fun fetchPokemons() {
        viewModelScope.launch {
            try {
                getAllPokemonUseCase.invoke()
                    .onStart { _viewState.value = ListViewState.Loading }
                    .collect{
                    _viewState.value = ListViewState.ItemsSearch(pokemons = it)
                }
            } catch (exception: Exception) {
                _viewState.value =
                    ListViewState.ErrorLoadingItems(exception.message ?: "Unknown error")
            }
        }
    }


    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            try {
                val respuesta = getAllPokemonUseCase.invoke()
                val filteredPagingData = if (query.isNullOrEmpty()) {
                    respuesta
                } else {
                    respuesta.map { pagingData ->
                        pagingData.filter { pokemon ->
                            pokemon.name.contains(query, ignoreCase = true)
                        }
                    }
                }
                filteredPagingData.onStart { _viewState.value = ListViewState.Loading }
                    .collect{
                    _viewState.value = ListViewState.ItemsSearch(pokemons = it)
                }

            } catch (exception: Exception) {
                _viewState.value = ListViewState.ErrorLoadingItems(exception.message ?: "Unknown error")
            }
        }
    }

}