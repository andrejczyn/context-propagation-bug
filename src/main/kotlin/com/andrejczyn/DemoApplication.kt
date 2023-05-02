package com.andrejczyn

import io.opentelemetry.api.trace.Span
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Hooks

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    Hooks.enableAutomaticContextPropagation()
    runApplication<DemoApplication>(*args)
}

@RestController()
class LogController {
    val logger = KotlinLogging.logger {}

    @GetMapping("/")
    suspend fun log2() = coroutineScope {
        val currentSpanBefore = Span.current()
        logger.info { "Before ${Thread.currentThread().name} $currentSpanBefore" }
        delay(10)
        val currentSpanAfter = Span.current()
        logger.info { "After ${Thread.currentThread().name} $currentSpanAfter" }
    }
}
