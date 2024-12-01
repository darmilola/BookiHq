package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class StatusImageModel(@SerialName("id") val imageId: String = "", @SerialName("link") val imageUrl: String = "",
                            @SerialName("caption") val caption: String? = "",@SerialName("preview") val preview: String = "",
                            @SerialName("width") val width: Int = 0, @SerialName("height") val height: Int = 0): Parcelable