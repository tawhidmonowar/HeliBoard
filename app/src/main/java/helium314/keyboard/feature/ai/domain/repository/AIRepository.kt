package helium314.keyboard.feature.ai.domain.repository

import helium314.keyboard.core.util.ResultWrapper
import helium314.keyboard.feature.ai.domain.model.QuickReply

interface AIRepository {
    suspend fun rephraseContent(
        content: String,
        language: String,
        tone: String
    ): ResultWrapper<String>

    suspend fun fixGrammar(
        content: String,
        language: String,
        action: String
    ): ResultWrapper<String>


    suspend fun wordTone(
        content: String,
        language: String,
        action: String
    ): ResultWrapper<String>

    suspend fun aiWritingAssistance(
        content: String,
        language: String,
        action: String
    ): ResultWrapper<String>

    suspend fun translate(
        content: String,
        language: String
    ): ResultWrapper<String>

    suspend fun quickReply(
        content: String,
        language: String,
    ): ResultWrapper<QuickReply>

}
