package domain.specialist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetReviewsRequest(@SerialName("specialist_id") val specialistId: Int)
@Serializable
data class TimeOffRequest(@SerialName("specialist_id") val specialistId: Int, @SerialName("time_id") val timeId: Int,
                          @SerialName("day") val day: Int, @SerialName("year") val year: Int, @SerialName("month") val month: Int)

@Serializable
data class GetSpecialistAvailableTimeRequest(@SerialName("specialist_id") val specialistId: Int,
                                             @SerialName("day") val day: Int,
                                             @SerialName("month") val month: Int,
                                             @SerialName("year") val year: Int)