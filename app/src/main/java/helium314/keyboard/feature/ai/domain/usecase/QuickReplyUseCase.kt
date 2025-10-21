package helium314.keyboard.feature.ai.domain.usecase

import helium314.keyboard.core.util.ResultWrapper
import helium314.keyboard.feature.ai.domain.model.QuickReply
import helium314.keyboard.feature.ai.domain.repository.AIRepository

class QuickReplyUseCase(
    private val aiRepository: AIRepository
) {
    suspend operator fun invoke(text: String, language: String): ResultWrapper<QuickReply> {
        if (text.isEmpty()) return ResultWrapper.Success(
            QuickReply(
                emptyList(),
                emptyList(),
                emptyList()
            )
        )
        return aiRepository.quickReply(text, language)
    }
}
