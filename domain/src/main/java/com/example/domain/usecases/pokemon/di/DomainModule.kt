package com.example.domain.usecases.pokemon.di

import com.example.domain.usecases.pokemon.GetAllPokemonUseCase
import com.example.domain.usecases.pokemon.GetPokemonUseCase
import com.example.domain.usecases.pokemon.IGetAllPokemonUseCase
import com.example.domain.usecases.pokemon.IGetPokemonUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun providerAllPokemonsUseCase(getAllPokemonUseCase: GetAllPokemonUseCase) : IGetAllPokemonUseCase

    @Binds
    fun providerGetPokemonUseCase(getPokemonUseCase: GetPokemonUseCase) : IGetPokemonUseCase

}