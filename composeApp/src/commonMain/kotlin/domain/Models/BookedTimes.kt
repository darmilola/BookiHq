package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookedTimes(@SerialName("time") val serviceTime: ServiceTime? = null)