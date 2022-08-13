package io.jongyun.graphinstagram

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
class GraphInstagramApplication

fun main(args: Array<String>) {
    runApplication<GraphInstagramApplication>(*args)
}
