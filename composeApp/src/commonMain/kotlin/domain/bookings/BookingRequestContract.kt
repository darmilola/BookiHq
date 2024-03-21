package domain.bookings

import domain.Models.ServiceStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetSpecialistsRequest(@SerialName("serviceTypeId") val serviceTypeId: Int,
                                 @SerialName("selectedDate") val selectedDate: String)

@Serializable
data class CreateAppointmentRequest(@SerialName("user_id") val userId: Int,
                                @SerialName("vendor_id") val vendorId: Int,
                                @SerialName("service_id") val serviceId: Int,
                                @SerialName("service_type_id") val serviceTypeId: Int,
                                @SerialName("specialist_id") val specialistId: Int,
                                @SerialName("recommendation_id") val recommendationId: Int?,
                                @SerialName("appointmentTime") val appointmentTime: Int,
                                @SerialName("appointmentDate") val appointmentDate: String,
                                @SerialName("serviceLocation") val serviceLocation: String,
                                @SerialName("serviceStatus") val serviceStatus: String,
                                @SerialName("isRecommendedAppointment") val isRecommendedAppointment: Boolean)

@Serializable
data class CreateAppointmentRequestArray(@SerialName("appointment_array") val appointmentArray: ArrayList<CreateAppointmentRequest>)