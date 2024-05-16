package com.spring.test

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

class MatchRequest(val ReportId: Int)

@Component
class MatcherUrl(
	@Value("#{environment.SCHEME}") private val scheme: String,
	@Value("#{environment.HOST}") private val host: String,
	@Value("#{environment.PORT}") private val port: String
){
	private val url: String = "$scheme://$host:$port"
	fun printUrl(){
		print(url)
	}
}

@SpringBootApplication
class TestApplication(val matcherUrl: MatcherUrl): CommandLineRunner {

	val LOG: Logger = LoggerFactory.getLogger(TestApplication::class.java)

	override fun run(vararg args: String?) {
		matcherUrl.printUrl()
	}
}

fun main(args: Array<String>) {
	runApplication<TestApplication>(*args)
}
