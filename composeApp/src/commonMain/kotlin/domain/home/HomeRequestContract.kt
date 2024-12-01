package domain.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetHomeRequest(@SerialName("userId") val userId: Long)

@Serializable
data class GetHomeRequestWithStatus(@SerialName("userId") val userId: Long, @SerialName("vendorPhone") val vendorPhone: String)