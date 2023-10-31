package com.example.domain.usecases.pokemon.mapper

import com.example.domain.usecases.pokemon.model.ListPokemonModel
import com.example.domain.usecases.pokemon.model.PokemonListModel
import com.example.domain.usecases.pokemon.model.PokemonModel
import com.example.network.pokemons.model.response.ListPokemonResponse
import com.example.network.pokemons.model.response.Result


fun ListPokemonResponse.toMapper() = ListPokemonModel(
    count = this.count,
    next = this.next,
    previous = this.previous,
    results = this.results.toLisModdel()
)


fun List<Result>.toLisModdel(): PokemonListModel {
    val resultList = mutableListOf<PokemonModel>()
    this.forEach {
        resultList.add(it.toModel())
    }

    return PokemonListModel(resultList)
}

fun Result.toModel() = PokemonModel(
    name = this.name,
    url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
        getDigitPokemon(
            this.url
        )
    }.png",
    number = getDigitPokemon(this.url),
)

fun getDigitPokemon(url: String): Int {
    val regex = Regex("/(\\d+)/$")
    val matchResult = regex.find(url)
    val numeroPokemon = matchResult?.groups?.get(1)?.value
    val numeroPokemonInt = numeroPokemon?.toIntOrNull()
    return numeroPokemonInt!!
}