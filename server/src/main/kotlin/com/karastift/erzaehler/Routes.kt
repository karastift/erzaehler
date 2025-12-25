package com.karastift.erzaehler

import ai.koog.ktor.llm
import ai.koog.prompt.structure.executeStructured
import com.karastift.erzaehler.domain.model.entities.Story
import com.karastift.erzaehler.domain.model.entities.Topic
import com.karastift.erzaehler.domain.model.enums.toSpeechSpeed
import com.karastift.erzaehler.domain.model.requests.VoiceRequest
import com.karastift.erzaehler.domain.model.requests.StoryRequest
import com.karastift.erzaehler.domain.model.requests.TopicRequest
import com.karastift.erzaehler.domain.model.responses.StoryResponse
import com.karastift.erzaehler.domain.model.responses.TopicResponse
import com.karastift.erzaehler.inference.ErzaehlerStandardModel
import com.karastift.erzaehler.prompts.storyFromTopicPrompt
import com.karastift.erzaehler.prompts.topicPrompt
import com.karastift.erzaehler.voice.CartesiaVoiceProvider
import com.karastift.erzaehler.voice.GenerationConfig
import com.karastift.erzaehler.voice.OutputFormat
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

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

        val voiceProvider: CartesiaVoiceProvider by call.application.dependencies
        val availableVoices = voiceProvider.listVoices(languageCode = storyParameters.languageCode)

        val voicesString = availableVoices.joinToString("\n") { voice ->
            "ID: ${voice.id}, Name: ${voice.name}, Description: ${voice.description}, Gender: ${voice.gender}, Language: ${voice.language}"
        }

        val story = llm().executeStructured<Story>(
            storyFromTopicPrompt(
                languageCode = storyParameters.languageCode,
                languageLevel = storyParameters.languageLevel,
                topic = storyParameters.topic,
                availableVoices = voicesString
            ),
            ErzaehlerStandardModel,
        ).getOrThrow().structure

        call.respond(HttpStatusCode.OK, StoryResponse(story = story))
    }
}

fun Route.generateVoice() {
    post("/voice") {

        val voiceParameters = call.receive<VoiceRequest>()

//        val resourcePath = "/response.wav"
//        val inputStream = this::class.java.getResourceAsStream(resourcePath)
//            ?: return@post call.respondText(
//                "File not found: $resourcePath",
//                status = HttpStatusCode.NotFound
//            )
//
//        val bytes2 = inputStream.readBytes()
//        inputStream.close()
//
//        return@post call.respondBytes(
//            bytes2,
//            contentType = ContentType.Audio.MPEG,
//            status = HttpStatusCode.OK
//        )

        val voiceProvider = call.application.dependencies.resolve<CartesiaVoiceProvider>()

        val bytes = voiceProvider.generateVoice(
            text = voiceParameters.dialog.text,
            voiceId = voiceParameters.voiceId,
            modelId = "sonic-3",
            languageCode = voiceParameters.languageCode,
            generationConfig = GenerationConfig(
                speed = voiceParameters.languageLevel.toSpeechSpeed(),
                emotion = voiceParameters.dialog.emotion,
            ),
        )

        call.respondBytes(bytes)
    }
}

//fun Route.debugListVoices() {
//    post("/list") {
//
//        val voiceProvider = call.application.dependencies.resolve<CartesiaVoiceProvider>()
//
//        val list = voiceProvider.listVoices(LanguageCode.KO)
//
//        call.respond(list)
//    }
//}
