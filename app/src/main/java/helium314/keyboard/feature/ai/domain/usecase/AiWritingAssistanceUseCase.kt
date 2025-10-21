package helium314.keyboard.feature.ai.domain.usecase


import helium314.keyboard.core.util.ResultWrapper
import helium314.keyboard.feature.ai.domain.repository.AIRepository

class AiWritingAssistanceUseCase(
    private val repository: AIRepository
) {
    suspend operator fun invoke(
        content: String,
        language: String,
        action: String
    ): ResultWrapper<String> {
        if (content.isBlank()) {
            return ResultWrapper.Success(content)
        }

        return repository.aiWritingAssistance(
            content = content,
            language = language,
            action = action
        )
    }
}
