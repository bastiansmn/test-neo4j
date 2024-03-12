package fr.bastiansmn.testneo4j

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestNeo4jApplication

fun main(args: Array<String>) {
	runApplication<TestNeo4jApplication>(*args)
}
