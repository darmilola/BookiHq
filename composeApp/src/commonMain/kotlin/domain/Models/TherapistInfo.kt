package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class TherapistInfo(@SerialName("id") val id: Long? = -1, @SerialName("isAvailable") val isAvailable: Boolean? = false,
                         @SerialName("isMobileServicesAvailable") val isMobileServiceAvailable: Boolean? = false,
                         @SerialName("profile_info") val profileInfo: User? = null, @SerialName("service_reviews") val reviews: List<AppointmentReview>? = listOf(),
                         @SerialName("booked_times") val bookedTimes: List<Appointment>? = arrayListOf(),val isSelected: Boolean = false): Parcelable

