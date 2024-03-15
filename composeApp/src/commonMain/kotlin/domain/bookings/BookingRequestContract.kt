package domain.bookings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetSpecialistsRequest(@SerialName("serviceTypeId") val serviceTypeId: Int, @SerialName("selectedDate") val selectedDate: String)