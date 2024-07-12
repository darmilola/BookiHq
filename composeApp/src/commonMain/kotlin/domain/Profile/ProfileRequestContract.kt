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
data class DeleteProfileRequest(@SerialName("userEmail") val userEmail: String)

@Serializable
data class GetPlatformCitiesRequest(@SerialName("country") val country: String)

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
                                @SerialName("meetingDescription") val meetingDescription: String,
                                @SerialName("appointmentType") val appointmentType: String)