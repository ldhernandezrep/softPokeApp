package com.example.pokedexapp.ui.listpokemon

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.domain.usecases.pokemon.model.PokemonModel
import kotlinx.coroutines.flow.Flow

sealed class ListViewState {

    object Loading: ListViewState()
    class ErrorLoadingItems(val message: String) : ListViewState()
    class ItemsSearch(val pokemons: PagingData<PokemonModel>) : ListViewState()
    class ItemNotSearch() : ListViewState()

}
