package com.example.pokedexapp.ui.listpokemon

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.usecases.pokemon.model.PokemonModel
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.FragmentListPokemonBinding
import com.example.pokedexapp.ui.common.observe
import com.example.pokedexapp.ui.listpokemon.adapter.PokemonsAdapterGrid
import com.example.pokedexapp.ui.listpokemon.adapter.PokemonsAdapterList
import com.example.pokedexapp.ui.listpokemon.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListPokemonFragment : Fragment(R.layout.fragment_list_pokemon), IClickPokemonListener {

    private lateinit var binding: FragmentListPokemonBinding
    private lateinit var pokemonAdapterGrid: PokemonsAdapterGrid
    private lateinit var pokemonAdapterList: PokemonsAdapterList
    private var isIconListVisible = true
    private var isIconGridVisible = false
    private val viewModel: ListViewModel by viewModels()
    var listPokemon: List<PokemonModel> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListPokemonBinding.bind(view)
        lifecycle.addObserver(viewModel)
        observe(viewModel.getViewState(), ::onViewState)
        viewModel.fetchPokemons()

    }

    private fun onViewState(state: ListViewState?) {
        when (state) {
            ListViewState.Loading -> {
                binding.llProgressBar.root.visibility = View.VISIBLE
                binding.llyMain.visibility = View.GONE
            }

            is ListViewState.ItemsSearch -> {
                binding.llProgressBar.root.visibility = View.GONE
                binding.llyMain.visibility = View.VISIBLE
                if(isIconGridVisible) {
                    setupRecyclerViewList()
                    lifecycleScope.launch {
                            pokemonAdapterList.submitData(state.pokemons)
                    }
                }
                else{
                    setUpRecyclerViewGrid()
                    lifecycleScope.launch {
                        pokemonAdapterGrid.submitData(state.pokemons)
                    }
                }
            }

            is ListViewState.ErrorLoadingItems -> {
                binding.llProgressBar.root.visibility = View.GONE
                binding.llyMain.visibility = View.VISIBLE
                //requireActivity().toast("Error al actualizar el producto")
            }

            else -> {

            }
        }
    }

    private fun setUpRecyclerViewGrid() {
        binding.rcvGrid.visibility = View.VISIBLE
        binding.rcvRow.visibility = View.GONE
        pokemonAdapterGrid =
            PokemonsAdapterGrid(this)
        val gridLayoutManager: GridLayoutManager = GridLayoutManager(context, 3)
        binding.rcvGrid.layoutManager = gridLayoutManager
        binding.rcvGrid.adapter = pokemonAdapterGrid
    }

    private fun setupRecyclerViewList() {
        pokemonAdapterList =
            PokemonsAdapterList(this)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rcvGrid.visibility = View.GONE
        binding.rcvRow.visibility = View.VISIBLE
        binding.rcvRow.layoutManager = linearLayoutManager
        binding.rcvRow.adapter = pokemonAdapterList
    }

    private fun configRecyclerList() {
        setupRecyclerViewList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_pekemon, menu)
        val searchItem = menu.findItem(R.id.action_search)
        (searchItem.actionView as SearchView?)?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val texto: String = query!!;
                viewModel.setSearchQuery(texto)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val texto: String = newText ?: "";
                viewModel.setSearchQuery(texto)
                return true
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val icon1: MenuItem? = menu?.findItem(R.id.action_list)
        val icon2: MenuItem? = menu?.findItem(R.id.action_grid)

        if (isIconListVisible) {
            icon1?.isVisible = true
            icon2?.isVisible = false
        } else {
            icon1?.isVisible = false
            icon2?.isVisible = true
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_list -> {
                isIconListVisible = false
                isIconGridVisible = true
                viewModel.fetchPokemons()
                requireActivity().invalidateOptionsMenu()
                true
            }

            R.id.action_grid -> {
                isIconGridVisible = false
                isIconListVisible = true
                viewModel.fetchPokemons()
                requireActivity().invalidateOptionsMenu()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(pokemonItem: PokemonModel) {
        findNavController().navigate(
            R.id.action_listPokemonFragment_to_detailPokemonFragment,
            bundleOf(
                "name_pokemon" to pokemonItem.name
            )

        )
    }
}