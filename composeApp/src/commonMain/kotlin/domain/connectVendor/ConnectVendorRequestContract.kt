package domain.connectVendor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectVendorRequest(@SerialName("userEmail") val userEmail: String,
                                @SerialName("vendorId") val vendorId: Int)
@Serializable
data class GetVendorRequest(@SerialName("country") val country: String)
@Serializable
data class SearchVendorRequest(@SerialName("country") val country: String,
                               @SerialName("query") val query: String)