package helium314.keyboard.core.di

import helium314.keyboard.feature.ai.data.repository.AIRepositoryImpl
import helium314.keyboard.feature.ai.domain.repository.AIRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AIRepository> { AIRepositoryImpl(get()) }
}
