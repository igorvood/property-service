package ru.vood.property.server.dao.abstraction

interface CommonDaoService<T> {

    fun getOne(entity: T): T

    fun getAll(): List<T>

    fun delete(entity: T)

    fun update(entity: T)

    fun insert(entity: T)
}