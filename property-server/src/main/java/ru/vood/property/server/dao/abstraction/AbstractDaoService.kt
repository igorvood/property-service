package ru.vood.property.server.dao.abstraction

import org.slf4j.LoggerFactory
import ru.vood.property.server.annotation.Column
import ru.vood.property.server.annotation.Pk
import ru.vood.property.server.annotation.Table
import ru.vood.property.server.dao.dto.FieldDto
import ru.vood.property.server.dao.dto.SqlOperation
import ru.vood.property.server.dao.dto.TableDto
import java.util.stream.IntStream

abstract class AbstractDaoService<T>(var clazz: Class<T>) : CommonDaoService<T> {
    private val logger = LoggerFactory.getLogger(AbstractDaoService::class.java)

    val fileds: Map<String, FieldDto>

    val table: TableDto

    val sqls = sortedMapOf<SqlOperation, String>()

    init {
        val tableAnnotation = clazz.getAnnotation(Table::class.java)
        this.table = TableDto(tableAnnotation.owner, tableAnnotation.name)
        this.fileds = this.clazz.declaredFields
                .map { FieldDto(getColumn(it.declaredAnnotations), it, it.getAnnotation(Pk::class.java) != null) }
                .map { it.column to it }
                .toMap()
        sqls[SqlOperation.INSERT] = getInsert(this.table, this.fileds)
        sqls[SqlOperation.UPDATE] = getUpdate(this.table, this.fileds)
        sqls[SqlOperation.DELETE] = getDelete(this.table, this.fileds)
        sqls.entries
                .forEach { logger.info("query for ${it.key.name} from table ${table.owner}.${table.tableName} ${it.value}") }
        println("1")
    }

    private fun getInsert(table: TableDto, fileds: Map<String, FieldDto>): String {
        val binds = IntStream.range(1, fileds.keys.size + 1)
                .mapToObj { ":$it" }
                .reduce { s1, s2 -> "$s1 , $s2" }
                .get()
        val columns = fileds.keys
                .reduce { s1, s2 -> "$s1 , $s2" }
        return "insert into ${table.owner}.${table.tableName}($columns) select $binds from dual"
    }

    private fun getUpdate(table: TableDto, fileds: Map<String, FieldDto>): String {
        var i = 1
        val set = fileds.values.stream()
                .filter { !it.isPk }
                .map { "${it.column} = :$i" }
                .peek { i++ }
                .reduce { s1, s2 -> "$s1 , $s2" }
                .get()
        val where = getWhereByPks(fileds, i)
        return "update ${table.owner}.${table.tableName} set $set where $where"
    }

    private fun getDelete(table: TableDto, fileds: Map<String, FieldDto>): String {
        val where = getWhereByPks(fileds, 1)
        return "delete ${table.owner}.${table.tableName} where $where"
    }

    private fun getWhereByPks(fileds: Map<String, FieldDto>, i: Int): String {
        var i = i
        return fileds.values.stream()
                .filter { it.isPk }
                .map { "${it.column} = :$i" }
                .peek { i++ }
                .reduce { s1, s2 -> "$s1 and $s2" }
                .get()
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