package helium314.keyboard.feature.ai.data.mapper

import helium314.keyboard.feature.ai.data.model.APIRequest
import helium314.keyboard.feature.ai.data.model.Content
import helium314.keyboard.feature.ai.data.model.Part

fun String.toAPIRequest(systemInstruction: String? = null): APIRequest {
    return APIRequest(
        contents = listOf(
            Content(
                parts = listOf(
                    Part(
                        text = this
                    )
                )
            )
        ),
        systemInstruction = Content(
            parts = listOf(
                Part(
                    text = systemInstruction
                )
            )
        )
    )
}
