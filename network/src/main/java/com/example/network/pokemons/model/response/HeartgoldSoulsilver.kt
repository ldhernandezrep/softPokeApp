package com.example.network.pokemons.model.response

data class HeartgoldSoulsilver(
    val back_default: String,
    val back_female: String? = null,
    val back_shiny: String,
    val back_shiny_female: String? = null,
    val front_default: String,
    val front_female: String? = null,
    val front_shiny: String,
    val front_shiny_female: String? = null
)