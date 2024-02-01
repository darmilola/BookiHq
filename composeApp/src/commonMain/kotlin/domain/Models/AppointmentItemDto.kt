package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentItemDto(
    @SerialName("id")
    val appointmentId: String,
)