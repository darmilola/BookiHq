package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class SpecialistInfo(@SerialName("id") val id: Int? = null, @SerialName("specialist_id") val specialistId: Int? = null,
                          @SerialName("rating") val rating: Double? = null, @SerialName("isAvailable") val isAvailable: Boolean? = false,
                          @SerialName("isAvailableForHomeService") val isAvailableForHomeService: Boolean? = false,
                          @SerialName("rateCount") val rateCount: Int? = 0, @SerialName("rateSum") val rateSum: Int? = 0, @SerialName("profile_info") val profileInfo: User? = null,
                          @SerialName("booked_times") val bookedTimes: List<BookedTimes>? = arrayListOf(),
                          @SerialName("time_offs") val timeOffs: List<TimeOffs>? = arrayListOf(), @SerialName("available_times") val availableTimes: List<AvailableTime>? = arrayListOf(),
                          @SerialName("specialist_reviews") val specialistReviews: List<SpecialistReviews>? = arrayListOf(), val isSelected: Boolean = false): Parcelable


data class TherapistUIModel(
    var selectedTherapistMap: HashMap<Int, SpecialistInfo>?,
    var salonTherapist: List<SpecialistInfo>
)
