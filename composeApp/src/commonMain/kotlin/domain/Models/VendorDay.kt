package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class VendorDay(@SerialName("id") val id: Int? = null, @SerialName("day_id") val dayId: Int? = null,
                      @SerialName("platform_day") val platformDay: PlatformDay? = null): Parcelable