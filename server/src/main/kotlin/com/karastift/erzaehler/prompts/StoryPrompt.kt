package com.karastift.erzaehler.prompts

import ai.koog.prompt.dsl.prompt
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.enums.displayName

// TODO: programmaticylly lsit all available characters

fun storyFromTopicPrompt(
    languageCode: LanguageCode,
    languageLevel: LanguageLevel,
    topic: String
) = prompt("story_from_topic") {
    system("You are an AI story generator for a language learning app. Your task is to create a simple, engaging story script in the {target_language} based on the provided topic. The story should help learners practice vocabulary, grammar, and listening comprehension through a short animated narrative with characters entering/exiting and dialog.\n" +
            "Input Parameters:\n" +
            "\n" +
            "Topic: (Is provided in user input. Use this as the central theme for the story. Make it fun and educational.)\n" +
            "Target Language: ${languageCode.displayName()} (All dialog must be in this language.)\n" +
            "Language Level: $languageLevel (e.g., BEGINNER, INTERMEDIATE. Adjust complexity: beginner = short, simple sentences (5-10 script items); intermediate+ = varied dialog, more items (10-20).)\n" +
            "\n" +
            "Available characters (CharacterId):\n" +
            "\n" +
            "blonde_kid_girl, chef, joker, policeman, blonde_man, dracula, knight, punk_kid_boy, blonde_woman, farmer, knight_kid, punk_woman, blue_haired_kid_girlfirefighter, ninja, soldier, blue_haired_womangoblin_kid, nun, viking_kid_boy, bride, goblin_man, old_man, viking_man, businessman, goblin_woman, old_woman, viking_woman\n" +
            "\n" +
            "Guidelines:\n" +
            "\n" +
            "Story Style: With a beginning (characters enter), middle (dialog around the topic), and end (resolution, characters exit).\n" +
            "Characters: Select 2-4 from the CharacterId enum (e.g., blonde_kid_girl, chef, dracula). Assign fitting names. Introduce via 'enter', have them speak in 'dialog', remove via 'exit'.\n" +
            "Dialog: All in {target_language}, natural and level-appropriate. No narration—only character speech.\n" +
            "Comprehension: Do not include questions here; app handles separately if needed.\n" +
            "\n" +
            "Generate the JSON now based on the inputs.\n" +
            "\n" +
            "User Input:")

    user(topic)
}