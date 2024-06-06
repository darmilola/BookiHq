package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class TimeOffs(@SerialName("id") val id: Int? = null, @SerialName("specialist_id") val specialistId: Int? = null,
                    @SerialName("time_id") val timeId: Int? = null,
                    @SerialName("time_off_time") val timeOffTime: TimeOffObject? = null, val isSelected: Boolean = false): Parcelable

@Serializable @Parcelize
data class TimeOffObject(@SerialName("id") val id: Int? = null, @SerialName("time_id") val timeId: Int? = null,
                         @SerialName("vendor_time") val vendorTime: VendorTime? = null): Parcelable