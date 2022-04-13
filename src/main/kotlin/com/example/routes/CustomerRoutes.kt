package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import com.example.models.Customer
import com.example.models.customerStorage

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            } else {
                call.respond(HttpStatusCode.NotFound, "No customers found")
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.NotFound, "No customer found")
            val customer = customerStorage.find { it.id == id } ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "No customer found"
            )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respond(HttpStatusCode.Created, customer)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.NotFound, "No customer found")
            if (customerStorage.removeIf { it.id == id }) {
                call.respond(HttpStatusCode.OK, "Customer deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "No customer found")
            }

        }
    }
}

