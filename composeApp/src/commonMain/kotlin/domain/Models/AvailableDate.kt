package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableDate(@SerialName("id") val id: Int? = null, @SerialName("vendor_id") val vendorId: Int? = null,
                         @SerialName("specialist_id") val specialistId: Int? = null, @SerialName("startDate") val startDate: String? = null,
                         @SerialName("endDate") val endDate: String? = null)