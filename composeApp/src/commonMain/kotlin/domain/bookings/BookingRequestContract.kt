package domain.bookings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetTherapistsRequest(@SerialName("serviceTypeId") val serviceTypeId: Long,
                                @SerialName("vendorId") val vendorId: Long,
                                @SerialName("day") val day: Int, @SerialName("month") val month: Int,
                                @SerialName("year") val year: Int)

@Serializable
data class CreatePendingBookingAppointmentRequest(@SerialName("user_id") val userId: Long,
                                                  @SerialName("vendor_id") val vendorId: Long,
                                                  @SerialName("service_id") val serviceId: Long,
                                                  @SerialName("service_type_id") val serviceTypeId: Long,
                                                  @SerialName("therapist_id") val therapistId: Long,
                                                  @SerialName("appointmentTime") val appointmentTime: Int,
                                                  @SerialName("day") val day: Int,
                                                  @SerialName("month") val month: Int,
                                                  @SerialName("year") val year: Int,
                                                  @SerialName("serviceLocation") val serviceLocation: String,
                                                  @SerialName("serviceStatus") val serviceStatus: String,
                                                  @SerialName("bookingStatus") val bookingStatus: String,
                                                  @SerialName("appointmentType") val appointmentType: String,)

@Serializable
data class CreatePendingBookingPackageAppointmentRequest(@SerialName("user_id") val userId: Long,
                                                  @SerialName("vendor_id") val vendorId: Long,
                                                  @SerialName("package_id") val packageId: Long,
                                                  @SerialName("appointmentTime") val appointmentTime: Int,
                                                  @SerialName("day") val day: Int,
                                                  @SerialName("month") val month: Int,
                                                  @SerialName("year") val year: Int,
                                                  @SerialName("serviceLocation") val serviceLocation: String,
                                                  @SerialName("serviceStatus") val serviceStatus: String,
                                                  @SerialName("bookingStatus") val bookingStatus: String,
                                                  @SerialName("appointmentType") val appointmentType: String,)

@Serializable
data class CreateAppointmentRequest(@SerialName("user_id") val userId: Long,
                                           @SerialName("vendor_id") val vendorId: Long,
                                           @SerialName("day") val day: Int,
                                           @SerialName("month") val month: Int,
                                           @SerialName("year") val year: Int,
                                           @SerialName("bookingStatus") val bookingStatus: String,
                                           @SerialName("paymentAmount") val paymentAmount: Int,
                                           @SerialName("paymentMethod") val paymentMethod: String)

@Serializable
data class GetPendingBookingAppointmentRequest(@SerialName("user_id") val userId: Long, @SerialName("bookingStatus") val bookingStatus: String)

@Serializable
data class DeletePendingBookingAppointmentRequest(@SerialName("id") val pendingAppointmentId: Long)