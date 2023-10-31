package com.example.network.pokemons.model.response

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)