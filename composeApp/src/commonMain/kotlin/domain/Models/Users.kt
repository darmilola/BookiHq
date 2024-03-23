package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val userId: Int? = null, @SerialName("userEmail") val userEmail: String? = null, @SerialName("firstname") val firstname: String? = null,
    @SerialName("lastname") val lastname: String? = null, @SerialName("address") val address: String? = null, @SerialName("contactPhone") val contactPhone: String? = null,
    @SerialName("countryId") val countryId: Int? = -1, @SerialName("cityId") val cityId: Int? = -1, @SerialName("gender") val gender: String? = null,
    @SerialName("profileImageUrl") val profileImageUrl: String? = null, @SerialName("connectedVendor") val connectedVendor: Int? = null, @SerialName("isTherapist") val isTherapist: Boolean? = false,
    @SerialName("specialistInfo") val specialistInfo: SpecialistInfo? = null)

