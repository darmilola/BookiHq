package domain.Models

import domain.Enums.ProfileStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(@SerialName("status") val status: String = "", @SerialName("message") val message: String = "")

@Serializable
data class JoinMeetingResponse(@SerialName("status") val status: String = "", @SerialName("token") val token: String = "")

@Serializable
data class AuthenticationResponse(@SerialName("status") val status: String = "",  @SerialName("data") val userInfo: User = User(),
                                  @SerialName("profile_status") val profileStatus: String = ProfileStatus.COMPLETE_PROFILE.toPath())

@Serializable
data class HomePageResponse(@SerialName("status") val status: String = "", @SerialName("homePage") val homepageInfo: HomepageInfo = HomepageInfo(),
                            @SerialName("vendorStatus") val vendorStatus: ArrayList<VendorStatusModel> = arrayListOf())

@Serializable
data class ServiceTherapistsResponse(@SerialName("status") val status: String = "", @SerialName("therapists") val serviceTherapists: List<ServiceTypeTherapists>)


@Serializable
data class TherapistReviewsResponse(@SerialName("status") val status: String = "", @SerialName("reviews") val reviews: List<TherapistReviews>)


@Serializable
data class TherapistAvailabilityResponse(@SerialName("status") val status: String = "",
                                         @SerialName("bookings") val bookedAppointment: List<Appointment>)

@Serializable
data class TherapistTimeAvailabilityResponse(@SerialName("status") val status: String = "",
                                             @SerialName("availability") val availableTimes: List<AvailableTime>)

@Serializable
data class VendorAvailabilityResponse(@SerialName("status") val status: String = "",
                                          @SerialName("availableTimes") val availableTimes: List<VendorTime>)