package com.example.domain.usecases.pokemon.mapper

import Ability
import com.example.domain.usecases.pokemon.model.AbilitiesListModel
import com.example.domain.usecases.pokemon.model.AbilityModel
import com.example.domain.usecases.pokemon.model.AbilityModelX
import com.example.domain.usecases.pokemon.model.ImagePokemon
import com.example.domain.usecases.pokemon.model.PokemonDetail
import com.example.domain.usecases.pokemon.model.PokemonListModel
import com.example.domain.usecases.pokemon.model.PokemonModel
import com.example.network.pokemons.model.response.AbilityX
import com.example.network.pokemons.model.response.PokemonDetailResponse
import com.example.network.pokemons.model.response.Result
import com.example.network.pokemons.model.response.Sprites

fun PokemonDetailResponse.map() = PokemonDetail(
    base_experience = this.base_experience,
    height = this.height,
    id = this.id,
    is_default = this.is_default,
    location_area_encounters = this.location_area_encounters,
    name = this.name,
    order = this.order,
    weight = this.weight,
    abilities = this.abilities.toLisModdel(),
    imagePokemon = this.sprites.map()
)

fun Sprites.map() = ImagePokemon(
    back_default = this.back_default,
    front_shiny = this.front_shiny
)

fun Ability.toModel() = AbilityModel(
    slot = this.slot,
    is_hidden = this.is_hidden,
    ability = this.ability.toModel()
)

fun AbilityX.toModel() = AbilityModelX(
    name = this.name,
    url = this.url
)

fun List<Ability>.toLisModdel(): AbilitiesListModel {
    val resultList = mutableListOf<AbilityModel>()
    this.forEach {
        resultList.add(it.toModel())
    }

    return AbilitiesListModel(resultList)
}