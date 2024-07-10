package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.ServiceStatusEnum
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class CurrentAppointmentBooking(val bookingId: Int = -1, var isMobileService: Boolean = false, var serviceId: Int = -1,
                                     var serviceTypeId: Int? = -1, var appointmentTime: PlatformTime? = null, var day: Int = -1,
                                     var month: Int = -1, var year: Int = -1, var isRecommendedAppointment: Boolean = false,
                                     var recommendationId: Int? = null, var serviceTypeTherapists: ServiceTypeTherapists? = null,
                                     var serviceTypeItem: ServiceTypeItem? = null, var services: Services? = null, val serviceStatus: String = ServiceStatusEnum.PENDING.toPath()): Parcelable