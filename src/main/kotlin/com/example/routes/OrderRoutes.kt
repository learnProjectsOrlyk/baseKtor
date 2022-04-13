package com.example.routes

import com.example.models.OrderItem
import com.example.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*



fun Route.orderRouting() {
    route("/order") {
        get {
            if (orderStorage.isNotEmpty()) {
                call.respond(orderStorage)
            } else {
                call.respond(HttpStatusCode.NotFound, "No orders found")
            }
        }

        get("{number?}") {
            val number = call.parameters["number"] ?: return@get call.respond(HttpStatusCode.NotFound, "No id provided")
            val order =
                orderStorage.find { it.number == number } ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    "not found"
                )
            call.respond(order)
        }

        get("{number}/total") {
            val number =
                call.parameters["number"] ?: return@get call.respond(HttpStatusCode.NotFound, "order not found")
            val order = orderStorage.find { it.number == number } ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "No order found"
            )
            val total = order.contents.fold(0.0) { acc, item -> acc + item.amount * item.price }
            call.respond(total)
        }

    }

}
