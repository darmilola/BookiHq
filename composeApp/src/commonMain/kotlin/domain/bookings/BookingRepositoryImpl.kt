package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse
import domain.Profile.DeleteProfileRequest
import domain.Profile.ProfileNetworkService
import domain.Profile.ProfileRepository
import domain.Profile.UpdateProfileRequest
import io.ktor.client.HttpClient

class BookingRepositoryImpl(apiService: HttpClient): BookingRepository {

    private val bookingNetworkService: BookingNetworkService = BookingNetworkService(apiService)

    override suspend fun getServiceTherapist(
        serviceTypeId: Int,
        selectedDate: String
    ): Single<ServiceSpecialistsResponse> {
        val param = GetSpecialistsRequest(serviceTypeId, selectedDate)
        return bookingNetworkService.getSpecialists(param)
    }

}