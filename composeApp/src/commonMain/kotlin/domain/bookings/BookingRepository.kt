package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Enums.PaymentMethod
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface BookingRepository {
    suspend fun getServiceTherapist(serviceTypeId: Int, vendorId: Long): Single<ServiceTherapistsResponse>

    suspend fun createAppointment(userId: Long, vendorId: Long, service_id: Int, serviceTypeId: Int, therapist_id: Int,
                                  appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String,
                                  appointmentType: String, paymentAmount: Double, paymentMethod: String): Single<ServerResponse>


}

