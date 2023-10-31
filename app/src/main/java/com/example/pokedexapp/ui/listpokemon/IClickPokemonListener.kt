package com.example.pokedexapp.ui.listpokemon

import com.example.domain.usecases.pokemon.model.PokemonModel

interface IClickPokemonListener {
    fun onClick(pokemonItem: PokemonModel)
}