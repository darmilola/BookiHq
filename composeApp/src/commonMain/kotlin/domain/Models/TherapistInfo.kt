package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class TherapistInfo(@SerialName("id") val id: Long? = -1, @SerialName("therapist_id") val therapistId: Long? = -1,
                         @SerialName("isAvailable") val isAvailable: Boolean? = false,
                         @SerialName("isMobileServicesAvailable") val isMobileServiceAvailable: Boolean? = false,
                         @SerialName("profile_info") val profileInfo: User? = null,
                         @SerialName("booked_times") val bookedTimes: List<BookedTimes>? = arrayListOf(),
                         @SerialName("therapist_reviews") val therapistReviews: List<TherapistReviews>? = arrayListOf(), val isSelected: Boolean = false): Parcelable

