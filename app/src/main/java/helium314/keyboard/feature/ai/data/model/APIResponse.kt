package helium314.keyboard.feature.ai.data.model

import kotlinx.serialization.Serializable

@Serializable
data class APIResponse(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: ContentData
)

@Serializable
data class ContentData(
    val parts: List<PartData>
)

@Serializable
data class PartData(
    val text: String
)
