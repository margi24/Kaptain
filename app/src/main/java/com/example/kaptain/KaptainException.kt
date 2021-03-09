package com.example.kaptain

import java.lang.Exception

sealed class KaptainException: Exception()

data class EmptyResultException(private val msg: String) : KaptainException()
