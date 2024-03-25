package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeOffs(@SerialName("id") val id: Int? = null, @SerialName("specialist_id") val specialistId: Int? = null,
                    @SerialName("time_id") val timeId: Int? = null, @SerialName("date") val date: String? = null,
                    @SerialName("time_off_times") val timeOffTime: ServiceTime? = null, val isSelected: Boolean = false)