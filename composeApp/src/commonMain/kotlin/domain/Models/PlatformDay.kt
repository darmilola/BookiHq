package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PlatformDay(@SerialName("id") val id: Int? = null, @SerialName("day") val day: String? = null,
                       val isSelected: Boolean = false): Parcelable