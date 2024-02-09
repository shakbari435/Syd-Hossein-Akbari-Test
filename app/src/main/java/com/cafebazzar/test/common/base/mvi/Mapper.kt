package com.cafebazzar.test.common.base.mvi

interface Mapper<From, To> {
    fun mapFrom(from: From?): To?
}

fun <From, To> Mapper<From, To>.listMap(items: List<From>): List<To> {

    val list : MutableList<To> = mutableListOf()

    items.forEach {
        val to = mapFrom(it)
        to?.let { t ->
            list.add(t)
        }
    }

    return list.toList()
}