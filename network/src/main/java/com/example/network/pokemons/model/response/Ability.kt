import com.example.network.pokemons.model.response.AbilityX

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)