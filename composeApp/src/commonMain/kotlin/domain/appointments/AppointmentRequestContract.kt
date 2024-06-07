package domain.appointments

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.StringFormat

@Serializable
data class GetAppointmentRequest(@SerialName("user_id") val userId: Int)

@Serializable
data class GetSpecialistAppointmentRequest(@SerialName("specialist_id") val specialistId: Int)

@Serializable
data class PostponeAppointmentRequest(@SerialName("user_id") val userId: Int,
                                      @SerialName("vendor_id") val vendorId: Int,
                                      @SerialName("service_id") val serviceId: Int,
                                      @SerialName("service_type_id") val serviceTypeId: Int,
                                      @SerialName("specialist_id") val specialistId: Int,
                                      @SerialName("recommendation_id") val recommendationId: Int?,
                                      @SerialName("appointmentTime") val appointmentTime: Int,
                                      @SerialName("day") val day: Int,
                                      @SerialName("month") val month: Int,
                                      @SerialName("year") val year: Int,
                                      @SerialName("serviceLocation") val serviceLocation: String,
                                      @SerialName("serviceStatus") val serviceStatus: String,
                                      @SerialName("isRecommendedAppointment") val isRecommendedAppointment: Boolean,
                                      @SerialName("appointment_id") val appointmentId: Int)


@Serializable
data class DeleteAppointmentRequest(@SerialName("appointment_id") val appointmentId: Int)

@Serializable
data class JoinMeetingRequest(@SerialName("custom_participant_id") val customParticipantId: String,
                              @SerialName("preset_name") val presetName: String,
                              @SerialName("meetingId") val meetingId: String)

@Serializable
data class GetSpecialistAvailabilityRequest(@SerialName("specialist_id") val specialistId: Int, @SerialName("day") val day: Int,
                                            @SerialName("month") val month: Int, @SerialName("year") val year: Int)