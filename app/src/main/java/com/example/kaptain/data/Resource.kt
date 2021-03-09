package com.example.kaptain.data

import com.example.kaptain.KaptainException

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val type: KaptainException? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null, type: KaptainException? = null) : Resource<T>(data, message, type)
}