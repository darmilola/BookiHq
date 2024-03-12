package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reviewer(@SerialName("id") val id: Int? = null, @SerialName("firstname") val firstname: String? = null, @SerialName("lastname") val lastname: String? = null,
                    @SerialName("profileImageUrl") val profileImageUrl: String? = null, @SerialName("gender") val gender: String? = null)