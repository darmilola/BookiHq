package domain.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetHomeRequest(@SerialName("userEmail") val userEmail: String,
                          @SerialName("connectedVendor") val connectedVendor: Int)