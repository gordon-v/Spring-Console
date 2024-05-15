package com.spring.test

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

class MatchRequest {
	val Name: String = "Report Name"
	val ReportId: Int = 1
}

@SpringBootApplication
class TestApplication: CommandLineRunner {

	val LOG: Logger = LoggerFactory.getLogger(TestApplication::class.java)


	override fun run(vararg args: String?) {

	}
}

fun main(args: Array<String>) {
	runApplication<TestApplication>(*args)
}
