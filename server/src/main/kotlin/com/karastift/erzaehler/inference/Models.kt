package com.karastift.erzaehler.inference

import ai.koog.prompt.executor.clients.google.GoogleModels


val ErzaehlerStandardModel = GoogleModels.Gemini2_5Flash

//val ErzaehlerStandardModel = CustomModels.TestModel
//
//object CustomModels {
//    private val standardCapabilities: List<LLMCapability> = listOf(
//        LLMCapability.Temperature,
//        LLMCapability.Completion,
//        LLMCapability.MultipleChoices,
//    )
//
//    private val structuredOutputCapabilities: List<LLMCapability.Schema.JSON> = listOf(
//        LLMCapability.Schema.JSON.Basic,
//        LLMCapability.Schema.JSON.Standard,
//    )
//
//    val Gemma3_27B = LLModel(
//        provider = LLMProvider.Google,
//        id = "gemma-3-27b",
//        capabilities = standardCapabilities + structuredOutputCapabilities,
//        contextLength = 128_000,
//        maxOutputTokens = 8192,
//    )
//
//    val TestModel = LLModel(
//        provider = LLMProvider.Google,
//        id = "gpt-oss-120b",
//        capabilities = standardCapabilities + structuredOutputCapabilities,
//        contextLength = 128_000,
//        maxOutputTokens = 8192,
//    )
//}