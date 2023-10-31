package com.example.network.pokemons.model.response

data class XY(
    val front_default: String,
    val front_female: String? = null,
    val front_shiny: String,
    val front_shiny_female: String? = null
)