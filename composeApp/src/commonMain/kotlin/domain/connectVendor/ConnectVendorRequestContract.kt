package domain.connectVendor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectVendorRequest(@SerialName("userId") val userId: Long,
                                @SerialName("vendorId") val vendorId: Long,
                                @SerialName("action") val action: String)
@Serializable
data class GetVendorRequest(@SerialName("country") val country: String, @SerialName("city") val city: String, @SerialName("connectedVendor") val connectedVendor: Long)
@Serializable
data class ViewVendorRequest(@SerialName("country") val country: String, @SerialName("city") val city: String, @SerialName("connectedVendor") val connectedVendor: Long)
@Serializable
data class SearchVendorRequest(@SerialName("country") val country: String, @SerialName("query") val query: String, @SerialName("connectedVendor") val connectedVendor: Long)