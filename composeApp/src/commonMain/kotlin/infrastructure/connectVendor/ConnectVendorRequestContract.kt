package infrastructure.connectVendor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectVendorRequest(@SerialName("userEmail") val userEmail: String,
                                @SerialName("vendorId") val vendorId: Int)
@Serializable
data class GetVendorRequest(@SerialName("cityId") val cityId: Int,
                            @SerialName("countryId") val countryId: Int)
@Serializable
data class SearchVendorRequest(@SerialName("cityId") val cityId: Int,
                               @SerialName("countryId") val countryId: Int,
                               @SerialName("query") val query: String)