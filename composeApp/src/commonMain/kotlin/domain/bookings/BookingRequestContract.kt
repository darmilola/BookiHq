package domain.bookings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetTherapistsRequest(@SerialName("serviceTypeId") val serviceTypeId: Int,
                                @SerialName("day") val day: Int,
                                @SerialName("month") val month: Int,
                                @SerialName("year") val year: Int)

@Serializable
data class CreateAppointmentRequest(@SerialName("user_id") val userId: Int,
                                    @SerialName("vendor_id") val vendorId: Int,
                                    @SerialName("service_id") val serviceId: Int,
                                    @SerialName("service_type_id") val serviceTypeId: Int,
                                    @SerialName("therapist_id") val therapistId: Int,
                                    @SerialName("recommendation_id") val recommendationId: Int?,
                                    @SerialName("appointmentTime") val appointmentTime: Int,
                                    @SerialName("day") val day: Int,
                                    @SerialName("month") val month: Int,
                                    @SerialName("year") val year: Int,
                                    @SerialName("serviceLocation") val serviceLocation: String,
                                    @SerialName("serviceStatus") val serviceStatus: String,
                                    @SerialName("isRecommendedAppointment") val isRecommendedAppointment: Boolean)

@Serializable
data class CreateAppointmentRequestArray(@SerialName("appointment_array") val appointmentArray: ArrayList<CreateAppointmentRequest>)