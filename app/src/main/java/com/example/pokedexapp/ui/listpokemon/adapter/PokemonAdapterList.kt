package com.example.pokedexapp.ui.listpokemon.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.domain.usecases.pokemon.model.PokemonModel
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.ItemRowBinding
import com.example.pokedexapp.ui.common.BaseViewHolder
import com.example.pokedexapp.ui.common.Comparator
import com.example.pokedexapp.ui.listpokemon.IClickPokemonListener

class PokemonsAdapterList(val iClickPokemonListener: IClickPokemonListener) : PagingDataAdapter<PokemonModel, PokemonsAdapterList.PokemonListViewHolder>(Comparator.PokemonComparator) {

    private var pokemonList: MutableList<PokemonModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder{
        val itemBinding =
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = PokemonListViewHolder(itemBinding, parent.context)
        return holder;
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val pokemon = getItem(position)
        when (holder) {
            is PokemonListViewHolder -> pokemon?.let { holder.bind(it) }
        }
    }


    inner class PokemonListViewHolder(var binding: ItemRowBinding, var context: Context) :
        BaseViewHolder<PokemonModel>(binding.root) {

        override fun bind(item: PokemonModel) {
            binding.tvNombrePokemonRow.text = item.name.uppercase()
            binding.tvNumeroPokemon.text = "# ${item.number.toString()}"
            binding.lnyRow.setOnClickListener {
                iClickPokemonListener.onClick(item)
            }

            Glide.with(context)
                .load(item.url)
                .centerCrop()
                .error(R.drawable.ic_search)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        e?.message?.let {
                            Log.d("error", it)
                        }
                        return true;
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val bmp =
                            (resource as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

                        Palette.from(bmp).generate { palette ->
                            palette?.dominantSwatch?.rgb?.let { colorValue ->
                                val gradientDrawable = GradientDrawable(
                                    GradientDrawable.Orientation.TL_BR,
                                    intArrayOf(Color.TRANSPARENT, colorValue)
                                )
                                gradientDrawable.shape = GradientDrawable.OVAL
                                binding.imPokemonRow.background = gradientDrawable
                            }
                        }
                        return false
                    }
                })
                .into(binding.imPokemonRow)

        }
    }

}