package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class Reviewer(@SerialName("id") val id: Int? = null, @SerialName("firstname") val firstname: String? = null, @SerialName("lastname") val lastname: String? = null,
                    @SerialName("profileImageUrl") val profileImageUrl: String? = null, @SerialName("gender") val gender: String? = null): Parcelable