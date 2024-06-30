package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class TherapistReviews(@SerialName("id") val id: Int? = null, @SerialName("therapist_id") val therapistId: Int? = null,
                            @SerialName("created_at") val reviewDate: String? = null, @SerialName("reviewText") val reviewText: String? = null,
                            @SerialName("user_id") val userId: Int? = null, @SerialName("reviewer") val reviewer: Reviewer? = null): Parcelable