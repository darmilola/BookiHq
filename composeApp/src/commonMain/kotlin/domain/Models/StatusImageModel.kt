package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusImageModel(@SerialName("id") val imageId: String = "", @SerialName("link") val imageUrl: String = "",
                            @SerialName("caption") val caption: String? = "",@SerialName("preview") val preview: String = "",
                            @SerialName("width") val width: Int = 0, @SerialName("height") val height: Int = 0)