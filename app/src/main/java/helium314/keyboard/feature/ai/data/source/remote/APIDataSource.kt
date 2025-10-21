package helium314.keyboard.feature.ai.data.source.remote

import helium314.keyboard.feature.ai.data.model.APIRequest
import helium314.keyboard.feature.ai.data.model.APIResponse

interface APIDataSource {

    suspend fun getResponseFromAPI(
        request: APIRequest
    ): APIResponse
}
