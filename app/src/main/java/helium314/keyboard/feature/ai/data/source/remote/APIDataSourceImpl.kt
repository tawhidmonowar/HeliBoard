package helium314.keyboard.feature.ai.data.source.remote

import helium314.keyboard.feature.ai.data.model.APIRequest
import helium314.keyboard.feature.ai.data.model.APIResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class APIDataSourceImpl(
    private val httpClient: HttpClient,
) : APIDataSource {

    override suspend fun getResponseFromAPI(request: APIRequest): APIResponse {

        val baseUrl =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent"
        return postRequest(baseUrl, request)
    }

    private suspend inline fun <reified T> postRequest(
        url: String,
        body: Any
    ): T = try {
        httpClient.post(url) {
            setBody(body)
        }.body()
    } catch (e: Exception) {
        throw Exception("Please try again later", e)
    }
}
