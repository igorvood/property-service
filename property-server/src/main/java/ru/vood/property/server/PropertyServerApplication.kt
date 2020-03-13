package ru.vood.property.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PropertyServerApplication

fun main(args: Array<String>) {
    runApplication<PropertyServerApplication>(*args)
}
