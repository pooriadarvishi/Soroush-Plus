package com.example.soroushplusproject.data.mappers

fun interface Mapper<T, K> {
    fun map(from: T): K
}