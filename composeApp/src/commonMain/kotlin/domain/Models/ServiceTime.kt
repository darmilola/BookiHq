package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceTime(@SerialName("id") val id: Int? = null, @SerialName("time") val time: String? = null,
                       @SerialName("isAm") val isAm: Boolean = false, var isAvailable: Boolean = true, val isSelected: Boolean = false)

data class AvailableTimeUIModel(
    val selectedTime: ServiceTime,
    val visibleTime: List<ServiceTime>)