package helium314.keyboard.feature.ai.domain.usecase

import helium314.keyboard.core.util.ResultWrapper
import helium314.keyboard.feature.ai.domain.repository.AIRepository

class FixGrammarUseCase(
    private val repository: AIRepository
) {
    suspend operator fun invoke(
        content: String,
        language: String,
        action: String
    ): ResultWrapper<String> {
        if (content.isBlank()) {
            return ResultWrapper.Success(content) // Return original text if empty
        }

        return repository.fixGrammar(
            content = content,
            language = language,
            action = action
        )
    }
}
