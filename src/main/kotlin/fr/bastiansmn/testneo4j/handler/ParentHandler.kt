package fr.bastiansmn.testneo4j.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ParentHandler {

    fun hello(): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .bodyValue(BodyInserters.fromValue("Hello, Spring 🌸 !"))
    }

}