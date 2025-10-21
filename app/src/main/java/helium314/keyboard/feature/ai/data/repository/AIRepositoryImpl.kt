package helium314.keyboard.feature.ai.data.repository

import android.util.Log
import helium314.keyboard.core.util.ResultWrapper
import helium314.keyboard.feature.ai.data.mapper.aiWritingAssistancePrompt
import helium314.keyboard.feature.ai.data.mapper.aiWritingAssistanceSystemPrompt
import helium314.keyboard.feature.ai.data.mapper.contentRephrasePrompt
import helium314.keyboard.feature.ai.data.mapper.contentRephraseSystemPrompt
import helium314.keyboard.feature.ai.data.mapper.fixGrammarPrompt
import helium314.keyboard.feature.ai.data.mapper.fixGrammarSystemPrompt
import helium314.keyboard.feature.ai.data.mapper.quickReplyPrompt
import helium314.keyboard.feature.ai.data.mapper.quickReplySystemPrompt
import helium314.keyboard.feature.ai.data.mapper.toAPIRequest
import helium314.keyboard.feature.ai.data.mapper.translatePrompt
import helium314.keyboard.feature.ai.data.mapper.translateSystemPrompt
import helium314.keyboard.feature.ai.data.mapper.wordTonePrompt
import helium314.keyboard.feature.ai.data.mapper.wordToneSystemPrompt
import helium314.keyboard.feature.ai.data.source.remote.APIDataSource
import helium314.keyboard.feature.ai.domain.model.QuickReply
import helium314.keyboard.feature.ai.domain.repository.AIRepository

class AIRepositoryImpl(
    private val remoteSource: APIDataSource
) : AIRepository {

    override suspend fun rephraseContent(
        content: String,
        language: String,
        tone: String
    ): ResultWrapper<String> {
        return try {
            val response = remoteSource.getResponseFromAPI(
                request = contentRephrasePrompt(
                    content = content,
                    language = language,
                    tone = tone
                ).toAPIRequest(contentRephraseSystemPrompt())
            )
            ResultWrapper.Success(response.candidates.first().content.parts.first().text)
        } catch (e: Exception) {
            ResultWrapper.Failure(e.message ?: "Unknown error")
        }
    }

    override suspend fun fixGrammar(
        content: String,
        language: String,
        action: String
    ): ResultWrapper<String> {
        return try {
            val response = remoteSource.getResponseFromAPI(
                request = fixGrammarPrompt(
                    content = content,
                    language = language,
                    action = action
                ).toAPIRequest(fixGrammarSystemPrompt())
            )
            ResultWrapper.Success(response.candidates.first().content.parts.first().text)
        } catch (e: Exception) {
            ResultWrapper.Failure(e.message ?: "Unknown error")
        }
    }

    override suspend fun quickReply(
        content: String,
        language: String
    ): ResultWrapper<QuickReply> {
        Log.d("QuickReplyModule", "Hit")
        return try {
            val response = remoteSource.getResponseFromAPI(
                request = quickReplyPrompt(content, language)
                    .toAPIRequest(quickReplySystemPrompt())
            )

            Log.d("QuickReplyModule", "$response")

            val text = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: ""

            // Parse the text into lists
            val positive = text.extractBulletList("Positive")
            val neutral = text.extractBulletList("Neutral")
            val negative = text.extractBulletList("Negative")

            ResultWrapper.Success(
                QuickReply(
                    positive = positive,
                    neutral = neutral,
                    negative = negative
                )
            )
        } catch (e: Exception) {
            ResultWrapper.Failure(e.message ?: "Unknown error")
        }
    }

    private fun String.extractBulletList(section: String): List<String> {
        val regex = Regex("$section:\\s*((?:- .+\\n?)+)", RegexOption.IGNORE_CASE)
        val match = regex.find(this)?.groups?.get(1)?.value ?: return emptyList()
        return match.lines()
            .map { it.removePrefix("-").trim() }
            .filter { it.isNotEmpty() }
    }


    override suspend fun wordTone(
        content: String,
        language: String,
        action: String
    ): ResultWrapper<String> {
        return try {
            val response = remoteSource.getResponseFromAPI(
                request = wordTonePrompt(
                    content = content,
                    language = language,
                    action = action
                ).toAPIRequest(wordToneSystemPrompt())
            )
            ResultWrapper.Success(response.candidates.first().content.parts.first().text)
        } catch (e: Exception) {
            ResultWrapper.Failure(e.message ?: "Unknown error")
        }
    }


    override suspend fun aiWritingAssistance(
        content: String,
        language: String,
        action: String
    ): ResultWrapper<String> {
        return try {
            val response = remoteSource.getResponseFromAPI(
                request = aiWritingAssistancePrompt(
                    content = content,
                    language = language,
                    action = action
                ).toAPIRequest(aiWritingAssistanceSystemPrompt())
            )
            ResultWrapper.Success(response.candidates.first().content.parts.first().text)
        } catch (e: Exception) {
            ResultWrapper.Failure(e.message ?: "Unknown error")
        }
    }

    override suspend fun translate(
        content: String,
        language: String
    ): ResultWrapper<String> {
        return try {
            val response = remoteSource.getResponseFromAPI(
                request = translatePrompt(
                    content = content,
                    language = language
                ).toAPIRequest(translateSystemPrompt())
            )
            ResultWrapper.Success(response.candidates.first().content.parts.first().text)
        } catch (e: Exception) {
            ResultWrapper.Failure(e.message ?: "Unknown error")
        }
    }
}


