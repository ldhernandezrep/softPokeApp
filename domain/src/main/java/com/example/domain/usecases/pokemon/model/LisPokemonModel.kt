package com.example.domain.usecases.pokemon.model


data class ListPokemonModel (val count: Int,
                             val next: String,
                             val previous: Any?,
                             val results: PokemonListModel)

data class PokemonListModel(val listaPokemon: List<PokemonModel> = listOf())
