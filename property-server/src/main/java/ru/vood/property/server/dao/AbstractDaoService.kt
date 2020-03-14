package ru.vood.property.server.dao

import org.springframework.util.ReflectionUtils
import ru.vood.property.server.annotation.Column
import sun.reflect.misc.ReflectUtil

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

        return declaredAnnotations
//                .map { getParentAnnotation() }
                .filter { it is Column }
                .map { (it as Column).name }
                .first()
    }
}