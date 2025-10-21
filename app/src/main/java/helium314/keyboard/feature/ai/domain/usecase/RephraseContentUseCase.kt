package helium314.keyboard.feature.ai.domain.usecase

import helium314.keyboard.feature.ai.domain.repository.AIRepository

class RephraseContentUseCase(
    private val repository: AIRepository
) {
    suspend operator fun invoke(
        content: String,
        tone: String,
        language: String
    ) = repository.rephraseContent(
        content = content,
        tone = tone,
        language = language
    )
}
