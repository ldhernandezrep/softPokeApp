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
import com.example.pokedexapp.databinding.IteRowGridBinding
import com.example.pokedexapp.databinding.ItemRowBinding
import com.example.pokedexapp.ui.common.BaseViewHolder
import com.example.pokedexapp.ui.common.Comparator
import com.example.pokedexapp.ui.listpokemon.IClickPokemonListener

class PokemonsAdapterGrid(val iClickPokemonListener: IClickPokemonListener) : PagingDataAdapter<PokemonModel, PokemonsAdapterGrid.PokemonGridViewHolder>(
    Comparator.PokemonComparator
) {

    private var pokemonList: MutableList<PokemonModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonGridViewHolder {
        val itemBinding =
            IteRowGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = PokemonGridViewHolder(itemBinding, parent.context)
        return holder;
    }

    override fun onBindViewHolder(holder: PokemonsAdapterGrid.PokemonGridViewHolder, position: Int) {
        val pokemon = getItem(position)
        when (holder) {
            is PokemonGridViewHolder -> pokemon?.let { holder.bind(it) }
        }
    }


    inner class PokemonGridViewHolder(var binding: IteRowGridBinding, var context: Context) :
        BaseViewHolder<PokemonModel>(binding.root) {

        override fun bind(item: PokemonModel) {
            binding.tvNamePokemon.text = item.name

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
                                val colors = intArrayOf(Color.TRANSPARENT, colorValue)
                                val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
                                binding.lnyGrid.background = gradientDrawable
                            }
                        }
                        return false
                    }
                })
                .into(binding.imvPokemon)
                 binding.lnyGrid.setOnClickListener {
                iClickPokemonListener.onClick(item)
            }

        }
    }

    fun setPokemonList(newList: List<PokemonModel>) {
        pokemonList.clear()
        pokemonList.addAll(newList)
        notifyDataSetChanged()
    }
}