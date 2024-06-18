package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PlatformTime(@SerialName("id") val id: Int? = null, @SerialName("time") val time: String? = null,
                       @SerialName("isAm") val isAm: Boolean = false): Parcelable