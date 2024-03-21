package domain.appointments

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAppointmentRequest(@SerialName("user_id") val userId: Int)
