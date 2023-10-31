package com.example.domain.usecases.pokemon.common

import androidx.paging.PagingData

fun <T : Any, R: Any> PagingData<T>.map(transform: (T) -> R): PagingData<R> {
    return map(transform)
}