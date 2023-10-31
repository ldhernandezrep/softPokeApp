package com.example.pokedexapp.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.usecases.pokemon.model.PokemonModel

class Comparator {
    companion object {
        public val PokemonComparator = object : DiffUtil.ItemCallback<PokemonModel>() {
            override fun areItemsTheSame(
                oldItem: PokemonModel,
                newItem: PokemonModel
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PokemonModel,
                newItem: PokemonModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}