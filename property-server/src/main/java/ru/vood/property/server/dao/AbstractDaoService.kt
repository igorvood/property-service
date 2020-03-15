package ru.vood.property.server.dao

import ru.vood.property.server.annotation.Column
import ru.vood.property.server.annotation.Pk

class AbstractDaoService<T> : CommonDaoService<T> {
    lateinit var clazz: Class<T>

    lateinit var fileds: HashMap<String, FieldDto>

    constructor(clazz: Class<T>) {
        this.clazz = clazz
        this.clazz.fields
                .map { FieldDto(getColumn(it.declaredAnnotations)) }
        val fields = this.clazz.fields
    }

    private fun getColumn(declaredAnnotations: Array<Annotation>): String {
        val first = declaredAnnotations.first { it is Column || it is Pk }
        if (first is Column)
            return first.name
        if (first is Pk)
            return first.name
        throw IllegalStateException("Annotation Column or Pk not found")
    }
}