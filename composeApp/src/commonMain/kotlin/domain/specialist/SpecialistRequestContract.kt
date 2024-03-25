package domain.specialist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetReviewsRequest(@SerialName("specialist_id") val specialistId: Int)
@Serializable
data class TimeOffRequest(@SerialName("specialist_id") val specialistId: Int, @SerialName("time_id") val timeId: Int,
                          @SerialName("date") val date: String)