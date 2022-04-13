package com.example.routes

import com.example.dao.dao
import com.example.models.Article
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*


fun Route.articleRoutes() {

    route("article") {

        get {
            call.respond(
                FreeMarkerContent(
                    "index.ftl",
                    mapOf("articles" to dao.allArticles()),
                )
            )
        }

        get("new") {
            call.respond(FreeMarkerContent("new.ftl", model = null))
        }

        post {
            val formParameters = call.receiveParameters()
            val title = formParameters.getOrFail("title")
            val body = formParameters.getOrFail("body")
            val newEntry = dao.addArticle(title, body)
            call.respondRedirect("/article/${newEntry?.id}")
        }

        get("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.getArticle(id))))
        }

        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.getArticle(id))))
        }

        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()

            when (formParameters.getOrFail("_action")) {
                "update" -> {
                    val title = formParameters.getOrFail("title")
                    val body = formParameters.getOrFail("body")
                    dao.editArticle(id, title, body)
                    call.respondRedirect("/article/${id}")
                }
                "delete" -> {
                    dao.deleteArticle(id)
                    call.respondRedirect("/article")

                }
            }
        }
    }

}
