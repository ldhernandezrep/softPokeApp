package com.example.network.pokemons.model.response

import com.squareup.moshi.Json

data class GenerationI(
    @Json(name = "red-blue")
    val redBlue: RedBlue,
    @Json(name = "yellow")
    val yellow: Yellow
)