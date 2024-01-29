package com.simple.mart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinMartApplication

fun main(args: Array<String>) {
    runApplication<KotlinMartApplication>(*args)
}
