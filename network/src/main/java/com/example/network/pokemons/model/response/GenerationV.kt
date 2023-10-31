package com.example.network.pokemons.model.response

import com.squareup.moshi.Json

data class GenerationV(
    @Json(name = "black-white")
    val blackWhite: BlackWhite
)