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
            "Suitability: Aim for universality unless tied to the target language's culture. Focus on topics a language learner will encounter in every day life (AND DONT ONLY USE THE ONCE I SUGGESTED)\n" +
            "\n" +
            "Generate the JSON now based on the inputs.\n" +
            "\n" +
            "User Input (Optional: A keyword, theme, or partial idea provided by the user, e.g., \"supermarket\", \"travel\", or empty/none. If provided, enhance it into a specific, fun topic. If empty or \"random\", generate one from scratch.):")

    user(suggestion.ifBlank { "<No user input provided>" })}


// Topic from Vocab prompt
/*
You are an AI story generator for a language learning app. Your task is to create a simple, engaging story script in the {target_language} based on the provided topic. The story should help learners practice vocabulary, grammar, and listening comprehension through a short animated narrative with characters entering/exiting and dialog.
Input Parameters:

Topic: {topic} (Use this as the central theme for the story. Make it fun and educational.)
Vocabulary List: {vocab_list} (A comma-separated list of words/phrases in {target_language}. Incorporate at least 80% naturally into the dialog if provided; otherwise, select 5-10 level-appropriate words related to the topic.)
Target Language: {target_language} (e.g., Spanish, French. All dialog must be in this language. Keep character names simple and universal.)
Language Level: {language_level} (e.g., beginner, intermediate. Adjust complexity: beginner = short, simple sentences (5-10 script items); intermediate+ = varied dialog, more items (10-20).)

Output Format:
Output in strict JSON format matching this Pydantic schema for easy parsing by the app. Do not include any extra text outside the JSON.
{
"characters": [
{
"id": "CharacterId enum value (e.g., 'blonde_kid_girl')",
"name": "Simple name for the character (in English or {target_language}, e.g., 'Anna')"
}
// 2-4 characters total, chosen from available IDs to fit the topic (e.g., use 'chef' for food themes)
],
"script": [
// List of script items: start with 'enter' for characters, then 'dialog', end with 'exit' where needed
{"type": "enter", "id": "character id (from characters list)"},
{"type": "dialog", "speaker": "character name", "text": "Spoken line in {target_language}"},
{"type": "exit", "id": "character id"}
]
}
Guidelines:

Story Style: Positive, educational, with a beginning (characters enter), middle (dialog around the topic), and end (resolution, characters exit). Use repetition for learning. Keep total script short for animation.
Characters: Select 2-4 from the CharacterId enum (e.g., blonde_kid_girl, chef, dracula). Assign fitting names. Introduce via 'enter', have them speak in 'dialog', remove via 'exit'.
Dialog: All in {target_language}, natural and level-appropriate. Incorporate vocab. No narration—only character speech.
Assets: Use premade character IDs; app handles cute, minimalistic drawn-like animations based on script.
Subtitles/Animation: App will generate subtitles from 'text' in dialog and animate enters/exits sequentially.
Comprehension: Do not include questions here; app handles separately if needed.

Generate the JSON now based on the inputs.
 */
