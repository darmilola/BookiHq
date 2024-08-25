package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class HomepageInfo (
    @SerialName("user_info") val userInfo: User? = User(),
    @SerialName("vendor_info") val vendorInfo: Vendor? = Vendor(),
    @SerialName("vendor_services") val vendorServices: ArrayList<Services>? = arrayListOf(),
    @SerialName("vendor_recommendations") val recommendationRecommendations: ArrayList<VendorRecommendation>? = arrayListOf(),
    @SerialName("recent_appointments") val recentAppointments: ArrayList<UserAppointment>? = arrayListOf(),
    @SerialName("therapist_info") val therapistInfo: TherapistInfo = TherapistInfo(),
): Parcelable
