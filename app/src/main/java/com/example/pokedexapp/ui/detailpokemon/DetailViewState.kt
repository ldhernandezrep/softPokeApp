package com.example.pokedexapp.ui.detailpokemon

import com.example.domain.usecases.pokemon.model.PokemonDetail

sealed class DetailViewState {
    object Loading: DetailViewState()
    object ErrorLoadingItems : DetailViewState()
    class ItemsLoaded(val pokemon: PokemonDetail) : DetailViewState()

}