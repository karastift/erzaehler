package com.karastift.erzaehler

import ai.koog.ktor.llm
import ai.koog.prompt.structure.executeStructured
import com.karastift.erzaehler.domain.model.entities.Story
import com.karastift.erzaehler.domain.model.entities.Topic
import com.karastift.erzaehler.domain.model.requests.AudioRequest
import com.karastift.erzaehler.domain.model.requests.StoryRequest
import com.karastift.erzaehler.domain.model.requests.TopicRequest
import com.karastift.erzaehler.domain.model.responses.StoryResponse
import com.karastift.erzaehler.domain.model.responses.TopicResponse
import com.karastift.erzaehler.inference.ErzaehlerStandardModel
import com.karastift.erzaehler.prompts.storyFromTopicPrompt
import com.karastift.erzaehler.prompts.topicPrompt
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import java.io.InputStream

fun Route.index() {
    get("/") {
        call.respondText("Hallo Welt!")
    }
}

fun Route.generateTopic() {
    post("/topic") {

        val topicParameters = call.receive<TopicRequest>()

        val topic = llm().executeStructured<Topic>(
            topicPrompt(
                languageCode = topicParameters.languageCode,
                languageLevel = topicParameters.languageLevel,
                suggestion = topicParameters.suggestion,
            ),
            ErzaehlerStandardModel,
        ).getOrThrow().structure

        call.respond(HttpStatusCode.OK, TopicResponse(topic = topic))
    }
}

fun Route.generateStory() {
    post("/story") {

        val storyParameters = call.receive<StoryRequest>()

        if (storyParameters.topic.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest)
        }

        val story = llm().executeStructured<Story>(
            storyFromTopicPrompt(
                languageCode = storyParameters.languageCode,
                languageLevel = storyParameters.languageLevel,
                topic = storyParameters.topic,
            ),
            ErzaehlerStandardModel,
        ).getOrThrow().structure

        call.respond(HttpStatusCode.OK, StoryResponse(story = story))
    }
}

fun Route.generateVoice() {
    post("/voice") {

        val audioParameters = call.receive<AudioRequest>()

//        val voiceProvier: CartesiaVoiceProvider by dependencies
//
//        val bytes = voiceProvier.generateVoice(
//            text = "Was geeeeeeeeehhhttt?? Ich habe heute echt gar kein Bock was zu machen. Hast du Lust vorbeizukommen?",
//            voiceId = "b7187e84-fe22-4344-ba4a-bc013fcb533e",
//            modelId = "sonic-3",
//            languageCode = LanguageCode.EN,
//        )
//
//        call.respondBytes(bytes)


        val inputStream: InputStream =
            this::class.java.classLoader
                .getResourceAsStream("test2.mp3")
                ?: return@post call.respond(
                    HttpStatusCode.NotFound,
                    "Audio file not found"
                )

        val bytes = inputStream.readBytes()

        call.respondBytes(
            bytes = bytes,
            contentType = ContentType.Audio.MPEG
        )
    }
}