package com.karastift.erzaehler

import ai.koog.ktor.llm
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.model.PromptExecutor
import ai.koog.prompt.structure.executeStructured
import com.karastift.erzaehler.domain.model.entities.Topic
import com.karastift.erzaehler.domain.model.requests.TopicRequest
import com.karastift.erzaehler.prompts.topicPrompt
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.index() {
    get("/") {
        call.respondText("Hallo Welt!")
    }
}

// TODO: Maybe proper error handling in the unlikely case of parsing failure
fun Route.generateTopic() {
    post("/topic") {

        val topicParameters = call.receive<TopicRequest>();

        val topic = llm().executeStructured<Topic>(
            topicPrompt(
                languageCode = topicParameters.languageCode,
                languageLevel = topicParameters.languageLevel,
                suggestion = topicParameters.suggestion ,
            ),
            GoogleModels.Gemini2_5FlashLite,
        ).getOrThrow().structure

        call.respond(HttpStatusCode.OK, topic)
    }
}

fun Route.generateStory() {
    post("/story") {
        call.respond(HttpStatusCode.OK, "TODO :D")
    }
}