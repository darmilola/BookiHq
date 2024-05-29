package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class TimeOffs(@SerialName("id") val id: Int? = null, @SerialName("specialist_id") val specialistId: Int? = null,
                    @SerialName("time_id") val timeId: Int? = null, @SerialName("date") val date: String? = null,
                    @SerialName("time_off_times") val timeOffTime: ServiceTime? = null, val isSelected: Boolean = false): Parcelable