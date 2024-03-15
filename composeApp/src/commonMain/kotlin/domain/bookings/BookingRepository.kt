package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse

interface BookingRepository {
    suspend fun getServiceTherapist(
        serviceTypeId: Int,
        selectedDate: String): Single<ServiceSpecialistsResponse>
}

