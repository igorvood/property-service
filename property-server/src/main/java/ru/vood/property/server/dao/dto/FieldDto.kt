package ru.vood.property.server.dao.dto

import java.lang.reflect.Field

data class FieldDto(val column: String, val it: Field, val isPk: Boolean)