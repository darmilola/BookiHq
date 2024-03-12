package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceTime(@SerialName("id") val id: Int? = null, @SerialName("vendor_id") val vendorId: Int? = null,
                       @SerialName("specialist_id") val specialistId: Int? = null, @SerialName("startTime") val startTime: String? = null,
                       @SerialName("endTime") val endTime: String? = null, @SerialName("isTimeAvailable") val isTimeAvailable: Boolean = false,
                       @SerialName("timeLength") val timeLength: Int? = null, @SerialName("beforeMidDay") val beforeMidDay: Boolean = false)