package com.example.network.pokemons.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sprites(
    val back_default: String,
    val back_female: String? = null,
    val back_shiny: String,
    val back_shiny_female: String? = null,
    val front_default: String,
    val front_female: String? = null,
    val front_shiny: String,
    val front_shiny_female: String? = null,
    val other: Other,
    val versions: Versions
)