package com.example.kaptain

inline val <reified T> T.TAG: String
    get() = T::class.java.simpleName