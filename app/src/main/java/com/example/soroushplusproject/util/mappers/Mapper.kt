package com.example.soroushplusproject.util.mappers

interface Mapper<T, K> {
    fun map(from: T): K
}