package me.l3m4rk.test.data.mappers

interface Mapper<T, R> {

    fun transform(list: List<T>): List<R>

    fun transform(item: T): R

}