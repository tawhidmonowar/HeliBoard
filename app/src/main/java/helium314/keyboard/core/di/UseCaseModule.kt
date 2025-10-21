package helium314.keyboard.core.di

import helium314.keyboard.feature.ai.domain.usecase.AiWritingAssistanceUseCase
import helium314.keyboard.feature.ai.domain.usecase.FixGrammarUseCase
import helium314.keyboard.feature.ai.domain.usecase.QuickReplyUseCase
import helium314.keyboard.feature.ai.domain.usecase.RephraseContentUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { RephraseContentUseCase(get()) }
    single { FixGrammarUseCase(get()) }
    single { QuickReplyUseCase(get()) }
    single { AiWritingAssistanceUseCase(get()) }
}
