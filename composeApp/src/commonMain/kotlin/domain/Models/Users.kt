package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class User(
    @SerialName("id") val userId: Long? = null, @SerialName("email") val email: String? = null, @SerialName("firstname") val firstname: String? = null,
    @SerialName("lastname") val lastname: String? = null, @SerialName("address") val address: String? = null, @SerialName("contactPhone") val contactPhone: String? = null,
    @SerialName("country") val country: String = "", @SerialName("city") val city: String = "", @SerialName("gender") val gender: String? = null,
    @SerialName("imageUrl") val profileImageUrl: String? = null,
    @SerialName("authPhone") val authPhone: String? = null, @SerialName("connectedVendor") val connectedVendor: Int? = null,
    @SerialName("isTherapist") val isTherapist: Boolean? = false): Parcelable

