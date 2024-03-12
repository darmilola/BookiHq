package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class VendorStatusModel(
    @SerialName("id") val statusId: Int = -1, @SerialName("vendor_id") val vendorId: Int = -1,
    @SerialName("image_url") val imageUrl: String = "", @SerialName("statusText") val statusText: String = "")