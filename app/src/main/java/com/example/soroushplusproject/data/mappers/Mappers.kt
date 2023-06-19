package com.example.soroushplusproject.data.mappers

fun <F, T> Mapper<F, T>.map(collection: Collection<F>) = collection.map { F -> map(F) }