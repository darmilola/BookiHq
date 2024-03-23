package domain.specialist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetReviewsRequest(@SerialName("specialist_id") val specialistId: Int)