package domain.bookings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetTherapistsRequest(@SerialName("serviceTypeId") val serviceTypeId: Int,
                                @SerialName("vendorId") val vendorId: Long)

@Serializable
data class GetAvailabilityRequest(@SerialName("therapist_id") val therapistId: Int,
                                  @SerialName("day") val day: Int, @SerialName("month") val month: Int, @SerialName("year") val year: Int)
@Serializable
data class CreateAppointmentRequest(@SerialName("user_id") val user_id: Long,
                                    @SerialName("vendor_id") val vendor_id: Long,
                                    @SerialName("service_id") val service_id: Int,
                                    @SerialName("service_type_id") val service_type_id: Int,
                                    @SerialName("therapist_id") val therapist_id: Int,
                                    @SerialName("appointmentTime") val appointmentTime: Int,
                                    @SerialName("day") val day: Int,
                                    @SerialName("month") val month: Int,
                                    @SerialName("year") val year: Int,
                                    @SerialName("serviceLocation") val serviceLocation: String,
                                    @SerialName("serviceStatus") val serviceStatus: String,
                                    @SerialName("appointmentType") val appointmentType: String,
                                    @SerialName("paymentAmount") val paymentAmount: Double,
                                    @SerialName("paymentMethod") val paymentMethod: String)