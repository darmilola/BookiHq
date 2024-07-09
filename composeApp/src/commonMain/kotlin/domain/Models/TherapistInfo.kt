package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class TherapistInfo(@SerialName("id") val id: Int? = null, @SerialName("therapist_id") val therapistId: Int? = -1,
                         @SerialName("rating") val rating: Double? = null, @SerialName("isAvailable") val isAvailable: Boolean? = false,
                         @SerialName("isMobileServicesAvailable") val isMobileServiceAvailable: Boolean? = false,
                         @SerialName("rateCount") val rateCount: Int? = 0, @SerialName("rateSum") val rateSum: Int? = 0, @SerialName("profile_info") val profileInfo: User? = null,
                         @SerialName("booked_times") val bookedTimes: List<BookedTimes>? = arrayListOf(),
                         @SerialName("therapist_reviews") val therapistReviews: List<TherapistReviews>? = arrayListOf(), val isSelected: Boolean = false): Parcelable

