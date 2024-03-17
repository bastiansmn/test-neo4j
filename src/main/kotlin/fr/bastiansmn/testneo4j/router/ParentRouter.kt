package fr.bastiansmn.testneo4j.router

import fr.bastiansmn.testneo4j.handler.ParentHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration(proxyBeanMethods = false)
class ParentRouter {

//    @Bean
//    fun route(parentHandler: ParentHandler): RouterFunction<ServerResponse> {
//        return RouterFunctions
//            .route(
//                GET("/api/hello")
//                    .and(accept(MediaType.APPLICATION_JSON)),
//                parentHandler::hello
//            )
//    }

}