package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*

import com.example.routes.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        static("/static") {
            resources("files")
        }
        get("/") {
            call.respondRedirect("article")
        }
        customerRouting()
        orderRouting()
        articleRoutes()
    }
}
