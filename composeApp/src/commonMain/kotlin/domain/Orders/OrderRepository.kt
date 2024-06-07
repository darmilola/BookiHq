package domain.Orders

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.AppointmentListDataResponse
import domain.Models.OrderListDataResponse
import domain.Models.ServerResponse
import domain.Models.SpecialistAvailabilityResponse

interface OrderRepository {
    suspend fun getUserOrders(userId: Int, nextPage: Int = 1): Single<OrderListDataResponse>
}