package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpecialistReviews(@SerialName("id") val id: Int? = null, @SerialName("specialist_id") val specialistId: Int? = null,
                             @SerialName("reviewDate") val reviewDate: String? = null, @SerialName("reviewText") val reviewText: String? = null,
                             @SerialName("user_id") val userId: Int? = null, @SerialName("isHidden") val isHidden: Boolean? = null, @SerialName("reviewer") val reviewer: Reviewer? = null)