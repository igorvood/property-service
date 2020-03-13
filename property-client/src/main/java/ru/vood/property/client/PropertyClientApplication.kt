package ru.vood.property.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class PropertyClientApplication

fun main(args: Array<String>) {
    runApplication<PropertyClientApplication>(*args)
}
