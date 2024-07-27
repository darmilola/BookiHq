package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.ProfileStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(@SerialName("status") val status: String = "", @SerialName("message") val message: String = "")

@Serializable
data class JoinMeetingResponse(@SerialName("status") val status: String = "", @SerialName("token") val token: String = "")

@Serializable
data class AuthenticationResponse(@SerialName("status") val status: String = "", @SerialName("whatsappPhone") val whatsAppPhone: String? = null, @SerialName("data") val userInfo: User = User(),
                                  @SerialName("profile_Status") val profileStatus: String = ProfileStatus.COMPLETE_PROFILE.toPath())

@Serializable
data class CompleteProfileResponse(@SerialName("status") val status: String = "", @SerialName("message") val message: String = "",
                                   @SerialName("profileId") val profileId: Int = -1,)

@Serializable
data class HomePageResponse(@SerialName("status") val status: String = "", @SerialName("homePage") val homepageInfo: HomepageInfo = HomepageInfo())

@Serializable
data class HomePageWithStatusResponse(@SerialName("status") val status: String = "", @SerialName("homePage") val homepageInfo: HomepageInfo = HomepageInfo(),
                            @SerialName("vendorStatus") val vendorStatusList: List<VendorStatusModel> = arrayListOf())

@Serializable
data class ServiceTherapistsResponse(@SerialName("status") val status: String = "", @SerialName("therapists") val serviceTherapists: List<ServiceTypeTherapists>,
                                     @SerialName("platformTime") val platformTimes: List<PlatformTime>? = null,
                                     @SerialName("vendorTime") val vendorTimes: List<VendorTime>? = null)

@Serializable @Parcelize
data class PlatformCountryCitiesResponse(@SerialName("status") val status: String = "",
                                         @SerialName("response") val countryCities: List<CountryCities>? = null): Parcelable

@Serializable
data class TherapistReviewsResponse(@SerialName("status") val status: String = "", @SerialName("reviews") val reviews: List<TherapistReviews>)


@Serializable
data class TherapistAvailabilityResponse(@SerialName("status") val status: String = "",
                                         @SerialName("bookedTimes") val bookedAppointment: List<Appointment>,
                                         @SerialName("platformTime") val platformTimes: List<PlatformTime>,
                                         @SerialName("vendorTime") val vendorTimes: List<VendorTime>)

@Serializable
data class VendorAccountResponse(@SerialName("status") val status: String = "",
                                         @SerialName("vendorInfo") val vendorInfo: Vendor)

@Serializable
data class TherapistTimeAvailabilityResponse(@SerialName("status") val status: String = "",
                                             @SerialName("availability") val availableTimes: List<AvailableTime>)

@Serializable
data class VendorAvailabilityResponse(@SerialName("status") val status: String = "",
                                          @SerialName("vendorTimes") val vendorTimes: List<VendorTime>,
                                          @SerialName("platformTimes") val platformTimes: List<PlatformTime>)

@Serializable
data class PendingAppointmentResponse(@SerialName("status") val status: String = "", @SerialName("message") val message: String = "", @SerialName("appointments") val appointments: List<UserAppointmentsData>? = null)