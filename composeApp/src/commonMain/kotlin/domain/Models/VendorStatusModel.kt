package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class VendorStatusModel(
    @SerialName("id") val statusId: String = "", @SerialName("timeStamp") val timeStamp: Long = -1,
    @SerialName("image") val statusImage: StatusImageModel? = null)
