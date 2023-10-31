package com.example.pokedexapp.ui.detailpokemon.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.pokemon.GetPokemonUseCase
import com.example.domain.usecases.pokemon.IGetAllPokemonUseCase
import com.example.domain.usecases.pokemon.model.PokemonModel
import com.example.pokedexapp.ui.detailpokemon.DetailViewState
import com.example.pokedexapp.ui.listpokemon.ListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel  @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase) :
    ViewModel(), LifecycleObserver {

    private val _viewState = MutableLiveData<DetailViewState>()
    fun getViewState() = _viewState


    fun fetchPokemon(name: String) {
        _viewState.value = DetailViewState.Loading

        viewModelScope.launch {
            try {
                val response =  getPokemonUseCase.invoke(name)
                _viewState.value =
                    response?.let { DetailViewState.ItemsLoaded(pokemon = it) }

            } catch (ex: Exception) {
                _viewState.value = DetailViewState.ErrorLoadingItems
            }
        }
    }

}