package domain.Profile

import com.badoo.reaktive.single.Single
import dev.jordond.compass.Place
import domain.Enums.PaymentMethod
import domain.Models.AuthenticationResponse
import domain.Models.PlatformCountryCitiesResponse
import domain.Models.ServerResponse
import domain.Models.VendorAccountResponse
import domain.Models.VendorAvailabilityResponse

interface ProfileRepository {
    suspend fun updateProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ): Single<ServerResponse>

    suspend fun deleteProfile(userEmail: String): Single<ServerResponse>
    suspend fun getVendorAccountInfo(vendorId: Long): Single<VendorAccountResponse>
    suspend fun joinSpa(vendorId: Long, therapistId: Long): Single<ServerResponse>
    suspend fun switchVendor(userId: Long, vendorId: Long, action: String,
                             exitReason: String): Single<ServerResponse>
    suspend fun getVendorAvailableTimes(vendorId: Long): Single<VendorAvailabilityResponse>
    suspend fun reverseGeocode(lat: Double, lng: Double): Single<Place?>

    suspend fun getPlatformCities(
        country: String
    ): Single<PlatformCountryCitiesResponse>

    suspend fun createMeetingAppointment(meetingTitle: String,userId: Long, vendorId: Long, serviceStatus: String,bookingStatus: String,appointmentType: String,
                                         appointmentTime: Int, day: Int, month: Int, year: Int, meetingDescription: String,
                                         paymentAmount: Double, paymentMethod: String): Single<ServerResponse>
}


