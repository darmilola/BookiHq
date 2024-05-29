package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class ServiceTime(@SerialName("id") val id: Int? = null, @SerialName("time") val time: String? = null,
                       @SerialName("isAm") val isAm: Boolean = false, var isAvailable: Boolean = true, val isSelected: Boolean = false): Parcelable

data class AvailableTimeUIModel(
    val selectedTime: ServiceTime,
    val visibleTime: List<ServiceTime>)