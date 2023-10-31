package com.example.domain.usecases.pokemon.model

data class PokemonDetail(val base_experience: Int,
                         val height: Int,
                         val id: Int,
                         val is_default: Boolean,
                         val location_area_encounters: String,
                         val name: String,
                         val order: Int,
                         val weight: Int,
                         val abilities: AbilitiesListModel,
                         val imagePokemon: ImagePokemon)
