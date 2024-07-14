package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable @Parcelize
data class VendorStatusModel(
    @SerialName("id") val statusId: String = "", @SerialName("timeStamp") val timeStamp: Long = -1,
    @SerialName("image") val statusImage: StatusImageModel? = null, @SerialName("video") val statusVideoModel: StatusVideoModel? = null,
    @SerialName("text") val statusText: StatusTextModel? = null): Parcelable
