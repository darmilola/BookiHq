package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class BookedTimes(@SerialName("day") val day: Int, @SerialName("month") val month: Int, @SerialName("year") val year: Int, @SerialName("time") val platformTime: PlatformTime? = null): Parcelable