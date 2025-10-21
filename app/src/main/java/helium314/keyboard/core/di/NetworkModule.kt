package helium314.keyboard.core.di

import helium314.keyboard.core.network.HttpClientProvider
import helium314.keyboard.feature.ai.data.source.remote.APIDataSource
import helium314.keyboard.feature.ai.data.source.remote.APIDataSourceImpl
import org.koin.dsl.module

val networkModule = module {
    single { HttpClientProvider.client }
    single<APIDataSource> { APIDataSourceImpl(get()) }
}
