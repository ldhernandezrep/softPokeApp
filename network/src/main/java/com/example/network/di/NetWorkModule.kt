package com.example.network.di

import com.example.network.api.PokemonApi
import com.example.network.common.Config
import com.example.network.pokemons.PokemonListService
import com.example.network.pokemons.PokemonListServiceImp
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val okHttpClient by lazy {
            val builder = OkHttpClient.Builder()
                .readTimeout(100L, TimeUnit.SECONDS)
                .writeTimeout(100L, TimeUnit.SECONDS)
                .connectTimeout(100L, TimeUnit.SECONDS)
            builder.build()
        }

        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonApi(retrofit: Retrofit): PokemonApi {
        return retrofit.create(PokemonApi::class.java)
    }

    @Singleton
    @Provides
    fun providePokemonNetworkService(pokemonApi: PokemonApi): PokemonListService {
        return PokemonListServiceImp(pokemonApi)
    }

}