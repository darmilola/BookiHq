package domain.bookings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetTherapistsRequest(@SerialName("serviceTypeId") val serviceTypeId: Int,
                                @SerialName("vendorId") val vendorId: Long)
@Serializable
data class CreatePendingBookingAppointmentRequest(@SerialName("user_id") val userId: Long,
                                                  @SerialName("vendor_id") val vendorId: Long,
                                                  @SerialName("service_id") val serviceId: Int,
                                                  @SerialName("service_type_id") val serviceTypeId: Int,
                                                  @SerialName("therapist_id") val therapistId: Int,
                                                  @SerialName("appointmentTime") val appointmentTime: Int,
                                                  @SerialName("day") val day: Int,
                                                  @SerialName("month") val month: Int,
                                                  @SerialName("year") val year: Int,
                                                  @SerialName("serviceLocation") val serviceLocation: String,
                                                  @SerialName("serviceStatus") val serviceStatus: String,
                                                  @SerialName("appointmentType") val appointmentType: String,
                                                  @SerialName("bookingStatus") val bookingStatus: String,
                                                  @SerialName("paymentMethod") val paymentMethod: String)

@Serializable
data class CreateAppointmentRequest(@SerialName("user_id") val userId: Long,
                                           @SerialName("vendor_id") val vendorId: Long,
                                           @SerialName("day") val day: Int,
                                           @SerialName("month") val month: Int,
                                           @SerialName("year") val year: Int,
                                           @SerialName("bookingStatus") val bookingStatus: String,
                                           @SerialName("paymentAmount") val paymentAmount: Double,
                                           @SerialName("paymentMethod") val paymentMethod: String)

@Serializable
data class GetPendingBookingAppointmentRequest(@SerialName("user_id") val userId: Long, @SerialName("bookingStatus") val bookingStatus: String)

@Serializable
data class DeletePendingBookingAppointmentRequest(@SerialName("id") val pendingAppointmentId: Long)