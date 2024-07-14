package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class StatusVideoModel(@SerialName("id") val videoId: String = "", @SerialName("mime_type") val mimeType: String = "",
                            @SerialName("file_size") val fileSize: Long = 0, @SerialName("link") val videoUrl: String = "",
                            @SerialName("caption") val caption: String = "", @SerialName("width") val width: Int = -1,
                            @SerialName("height") val height: Int = -1, @SerialName("preview") val preview: String = ""): Parcelable