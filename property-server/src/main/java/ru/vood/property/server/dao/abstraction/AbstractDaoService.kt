package ru.vood.property.server.dao.abstraction

import ru.vood.property.server.annotation.Column
import ru.vood.property.server.annotation.Pk
import ru.vood.property.server.annotation.Table
import ru.vood.property.server.dao.dto.FieldDto
import ru.vood.property.server.dao.dto.TableDto

abstract class AbstractDaoService<T> : CommonDaoService<T> {
    var clazz: Class<T>

    var fileds: Map<String, FieldDto>

    var table: TableDto

    constructor(clazz: Class<T>) {
        this.clazz = clazz
        val table = clazz.getAnnotation(Table::class.java)
        this.table = TableDto(table.owner, table.name)
        this.fileds = this.clazz.declaredFields
                .map { FieldDto(getColumn(it.declaredAnnotations), it, it.getAnnotation(Pk::class.java) != null) }
                .map { it.column to it }
                .toMap()
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