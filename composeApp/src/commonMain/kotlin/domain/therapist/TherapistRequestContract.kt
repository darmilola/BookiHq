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
data class UpdateTherapistAvailabilityRequest(@SerialName("id") val therapistId: Long, @SerialName("isAvailable") val isAvailable: Boolean,
                                              @SerialName("isMobileServicesAvailable") val  isMobileServiceAvailable: Boolean)

@Serializable
data class GetTherapistAppointmentRequest(@SerialName("therapist_id") val therapistId: Long)