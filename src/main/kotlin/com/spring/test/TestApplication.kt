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
import java.net.ConnectException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration

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

	fun getUrl(): String {
		return url
	}
}

@Component
class HTTPClient(val matcherUrl: MatcherUrl){

	val client: HttpClient = HttpClient.newHttpClient()

	fun sendGetRequest(){
		println("GET request to ${matcherUrl.getUrl()}")
		val request: HttpRequest? = HttpRequest.newBuilder()
			.uri(URI(matcherUrl.getUrl()))
			.GET()
			.timeout(Duration.ofSeconds(10))
			.build()
		var response: HttpResponse<String?>?
		try {
			response = client.send(request,BodyHandlers.ofString())
		}
		catch (e: ConnectException){
			response = null;
			println("Can't connect to ${matcherUrl.getUrl()}")
		}

		if(response == null){
			println("No response")
		}
		else if(response.statusCode()== 404){
			println("${matcherUrl.getUrl()} returned status code 404")
		}

	}
}

@SpringBootApplication
class TestApplication(val httpClient: HTTPClient): CommandLineRunner {

	override fun run(vararg args: String?) {
		httpClient.sendGetRequest()
	}
}

fun main(args: Array<String>) {
	runApplication<TestApplication>(*args)
}
