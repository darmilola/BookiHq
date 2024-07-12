package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.ServiceStatusEnum
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class CurrentAppointmentBooking(var isMobileService: Boolean = false, var serviceId: Int = -1,
                                     var serviceTypeId: Int? = -1, var appointmentTime: PlatformTime? = null, var day: Int = -1,
                                     var bookingKey: Int = -1, var month: Int = -1, var year: Int = -1, var serviceTypeTherapists: ServiceTypeTherapists? = null,
                                     var description : String = "",
                                     var serviceTypeItem: ServiceTypeItem? = null, var services: Services? = null, val serviceStatus: String = ServiceStatusEnum.PENDING.toPath()): Parcelable


data class CurrentAppointmentBookingItemUIModel(
    val selectedItem: CurrentAppointmentBooking?,
    val itemList: ArrayList<CurrentAppointmentBooking>
)