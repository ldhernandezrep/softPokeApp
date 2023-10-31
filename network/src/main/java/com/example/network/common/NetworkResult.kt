package com.example.network.common


sealed class NetworkResult<T> {
    data class  NetWorkSuccess<T>(val result: T): NetworkResult<T>()
    data class  NetworkFailure<T>(val networkError: NetworkError): NetworkResult<T>()
}