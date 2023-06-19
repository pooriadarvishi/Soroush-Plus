package com.example.soroushplusproject.util.mappers

fun interface Mapper<T, K> {
    fun map(from: T): K
}