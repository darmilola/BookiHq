package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.RecommendationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class TimeObject(@SerialName("id") val recommendationId: Long = -1, @SerialName("vendor_id") val vendorId: Long = -1,
                      @SerialName("time_id") val timeId: Int = -1, @SerialName("platform_time") val platformTime: PlatformTime = PlatformTime()): Parcelable
