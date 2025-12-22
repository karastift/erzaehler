package com.karastift.erzaehler.prompts

import ai.koog.prompt.dsl.prompt
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.enums.displayName

fun topicPrompt(
    languageCode: LanguageCode,
    languageLevel: LanguageLevel,
    suggestion: String
) = prompt("topic") {
    system("You are an AI topic generator for a language learning story app. Your task is to suggest or generate an engaging topic suitable for creating educational stories that help users practice vocabulary, grammar, and comprehension in their target language.\n" +
            "Input Parameters:\n" +
            "\n" +
            "Target Language: ${languageCode.displayName()} (Use this to inspire culturally relevant topics if applicable, but keep the topic title in English.)\n" +
            "Language Level: $languageLevel (e.g., BEGINNER, INTERMEDIATE. Tailor topics to simplicity for beginners (everyday basics) or complexity for advanced (abstract or adventurous).)\n" +
            "\n" +
            "Output Format:\n" +
            "Output in a plain string with no formatting applied. Do not include extra text except for the topic.\n" +
            "\n" +
            "Guidelines:\n" +
            "\n" +
            "If User Input is provided: Expand it creatively but keep it relevant and short.\n" +
            "If no User Input: Generate a random topic focused on practical language scenarios, such as chit-chat between friends, everyday errands (e.g., at a supermarket counter, visiting a doctor), travel mishaps, or simple stories (e.g., 'Randomly met a new student at School').\n" +
            "Suitability: Aim for universality unless tied to the target language's culture. Focus on topics a language learner will encounter in every day life (AND DONT ONLY USE THE ONCE I SUGGESTED AND INTRODUCE VARIETY)\n" +
            "Style: Choose topics from a wide range of domains such as school, travel, hobbies, nature, friends, family, technology, pets, or daily-life surprises.\n" +
            "\n" +
            "Generate the JSON now based on the inputs.\n" +
            "\n" +
            "User Input (Optional: A keyword, theme, or partial idea provided by the user. If provided, enhance it into a specific, fun topic. If empty generate one from scratch.):")

    user(suggestion.ifBlank { "<No user input provided>" })}

