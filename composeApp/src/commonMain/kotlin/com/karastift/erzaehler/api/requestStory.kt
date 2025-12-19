package com.karastift.erzaehler.api

import kotlin.random.Random

// TODO: Implement call to api (and remove this file)
fun generateStoryJson(topic: String): String {
    // Return mock JSON for now
    return """
        {
          "characters": [
            { "id": "policeman", "name": "policeman" },
            { "id": "businessman", "name": "businessman" }
          ],
          "script": [
            { "type": "enter", "id": "policeman" },
            { "type": "enter", "id": "businessman" },
            { "type": "dialog", "speaker": "policeman", "text": "Hello." },
            { "type": "dialog", "speaker": "businessman", "text": "Oh, hi!" },
            { "type": "dialog", "speaker": "policeman", "text": "Nice evening, isn't it?" },
            { "type": "exit", "id": "businessman" },
            { "type": "dialog", "speaker": "policeman", "text": "Wait, where did you go?" },
            { "type": "enter", "id": "businessman" },
            { "type": "dialog", "speaker": "businessman", "text": "Hallo ich bin wieder da" }
          ]
        }
    """
}

fun generateStoryTopic(): String {

    val list = listOf(
        "Interaktion im an der Selbstbedienungskasse bei Rewe",
        "Wer aus der WG hat meinen Aufstrich aufgebraucht??",
        "Studium ist bald vorbei und eigentlich will ich noch gar nicht arbeiten"
    )
    val randomIndex: Int = Random.nextInt(list.size)

    return list[randomIndex]
}
