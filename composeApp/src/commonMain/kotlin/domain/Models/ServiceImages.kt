package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceImages (@SerialName("id") val id: Int?, @SerialName("service_id") val serviceId: Int?, @SerialName("imageUrl") val imageUrl: String? = "")
