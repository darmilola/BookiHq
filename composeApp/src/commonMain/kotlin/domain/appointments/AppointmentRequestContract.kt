package domain.appointments

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAppointmentRequest(@SerialName("user_id") val userId: Long)

@Serializable
data class AddAppointmentReviewRequest(@SerialName("user_id") val userId: Long, @SerialName("appointment_id") val appointmentId: Long,
                                       @SerialName("vendor_id") val vendorId: Long, @SerialName("service_type_id") val serviceTypeId: Long,
                                       @SerialName("reviewText") val reviewText: String)

@Serializable
data class PostponeAppointmentRequest(@SerialName("user_id") val userId: Long,
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
                                      @SerialName("appointment_id") val appointmentId: Long,
                                      @SerialName("bookingStatus") val bookingStatus: String,
                                      @SerialName("paymentMethod") val paymentMethod: String)

@Serializable
data class DeleteAppointmentRequest(@SerialName("appointment_id") val appointmentId: Long)

@Serializable
data class JoinMeetingRequest(@SerialName("custom_participant_id") val customParticipantId: String,
                              @SerialName("preset_name") val presetName: String,
                              @SerialName("meetingId") val meetingId: String)

@Serializable
data class GetTherapistAvailabilityRequest(@SerialName("therapist_id") val therapistId: Long, @SerialName("vendorId") val vendorId: Long, @SerialName("day") val day: Int,
                                           @SerialName("month") val month: Int, @SerialName("year") val year: Int)