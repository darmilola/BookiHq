package domain.therapist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetReviewsRequest(@SerialName("therapist_id") val therapistId: Long)

@Serializable
data class ArchiveAppointmentRequest(@SerialName("appointmentId") val appointmentId: Long)

@Serializable
data class DoneAppointmentRequest(@SerialName("appointmentId") val appointmentId: Long)

@Serializable
data class GetTherapistAppointmentRequest(@SerialName("therapist_id") val therapistId: Long)