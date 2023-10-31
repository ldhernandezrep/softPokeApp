package com.example.network.pokemons.model.response

data class ListPokemonResponse (
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Result>
)