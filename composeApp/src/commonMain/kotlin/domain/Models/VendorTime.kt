package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class VendorTime(@SerialName("id") val id: Int? = null, @SerialName("time_id") val timeId: Int? = null,
                      @SerialName("platform_time") val platformTime: PlatformTime? = null): Parcelable