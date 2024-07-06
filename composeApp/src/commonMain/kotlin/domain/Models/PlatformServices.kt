package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PlatformServices(@SerialName("id") val serviceId: Int = -1, @SerialName("title") val title: String? = null,
                    @SerialName("widgetCode") val widgetCode: Int? = -1): Parcelable