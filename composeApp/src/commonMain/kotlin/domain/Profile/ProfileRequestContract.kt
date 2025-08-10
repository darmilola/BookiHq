package domain.Profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(@SerialName("firstname") val firstname: String,
                                @SerialName("lastname") val lastname: String,
                                @SerialName("userEmail") val userEmail: String,
                                @SerialName("address") val address: String,
                                @SerialName("contactPhone") val contactPhone: String,
                                @SerialName("countryId") val countryId: Int,
                                @SerialName("cityId") val cityId: Int,
                                @SerialName("gender") val gender: String,
                                @SerialName("profileImageUrl") val profileImageUrl: String)

@Serializable
data class DeleteProfileRequest(@SerialName("userId") val userId: Long)

@Serializable
data class SwitchVendorRequest(@SerialName("userId") val userId: Long, @SerialName("vendorId") val vendorId: Long,
                               @SerialName("action") val action: String, @SerialName("exit_reason") val exitReason: String,
                               @SerialName("exit_vendorId") val exitVendorId: Long)

@Serializable
data class GetVendorInfoRequest(@SerialName("vendor_id") val vendorId: Long)

@Serializable
data class JoinSpaRequest(@SerialName("vendor_id") val vendorId: Long, @SerialName("therapist_id") val therapistId: Long)

@Serializable
data class GetCountryStatesRequest(@SerialName("countryId") val countryId: Long)

@Serializable
data class GetVendorAvailabilityRequest(@SerialName("vendorId") val vendorId: Long)

@Serializable
data class CreateMeetingRequest(@SerialName("meeting_title") val meetingTitle: String, @SerialName("user_id") val user_id: Long,
                                @SerialName("vendor_id") val vendor_id: Long,
                                @SerialName("appointmentTime") val appointmentTime: Int,
                                @SerialName("day") val day: Int,
                                @SerialName("month") val month: Int,
                                @SerialName("year") val year: Int,
                                @SerialName("serviceStatus") val serviceStatus: String,
                                @SerialName("bookingStatus") val bookingStatus: String,
                                @SerialName("meetingDescription") val meetingDescription: String,
                                @SerialName("appointmentType") val appointmentType: String,
                                @SerialName("paymentAmount") val paymentAmount: Double,
                                @SerialName("paymentMethod") val paymentMethod: String)