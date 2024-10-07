package domain.Profile

import applications.location.createGeocoder
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.toSingle
import dev.jordond.compass.Place
import dev.jordond.compass.geocoder.Geocoder
import domain.Models.CountryCitiesResponse
import domain.Models.ServerResponse
import domain.Models.VendorAccountResponse
import domain.Models.VendorAvailabilityResponse
import io.ktor.client.HttpClient

class ProfileRepositoryImpl(apiService: HttpClient): ProfileRepository {

    private val profileNetworkService: ProfileNetworkService = ProfileNetworkService(apiService)
    private val geocoder: Geocoder = createGeocoder()

    override suspend fun updateProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ): Single<ServerResponse> {
        val param = UpdateProfileRequest(firstname, lastname, userEmail, address, contactPhone, countryId,cityId, gender, profileImageUrl)
        return profileNetworkService.updateProfile(param)
    }

    override suspend fun deleteProfile(userEmail: String): Single<ServerResponse> {
        val param = DeleteProfileRequest(userEmail)
        return profileNetworkService.deleteProfile(param)
    }

    override suspend fun getVendorAccountInfo(vendorId: Long): Single<VendorAccountResponse> {
        val param = GetVendorInfoRequest(vendorId)
        return profileNetworkService.getVendorInfo(param)
    }

    override suspend fun joinSpa(
        vendorId: Long,
        therapistId: Long
    ): Single<ServerResponse> {
        val param = JoinSpaRequest(vendorId, therapistId)
        return profileNetworkService.joinSpa(param)
    }

    override suspend fun switchVendor(
        userId: Long,
        vendorId: Long,
        action: String,
        exitReason: String
    ): Single<ServerResponse> {
        val param = SwitchVendorRequest(userId, vendorId, action, exitReason)
        return profileNetworkService.switchVendor(param)
    }

    override suspend fun getVendorAvailableTimes(vendorId: Long): Single<VendorAvailabilityResponse> {
        val param = GetVendorAvailabilityRequest(vendorId)
        return profileNetworkService.getVendorAvailableTimes(param)
    }

    override suspend fun reverseGeocode(lat: Double, lng: Double): Single<Place?> {
        return geocoder.reverse(lat, lng).getFirstOrNull().toSingle()
    }

    override suspend fun getCountryCities(country: String): Single<CountryCitiesResponse> {
        val param = GetCountryCitiesRequest(country)
        return profileNetworkService.getCountryCities(param)
    }

    override suspend fun createMeetingAppointment(
        meetingTitle: String,
        userId: Long,
        vendorId: Long,
        serviceStatus: String,
        bookingStatus: String,
        appointmentType: String,
        appointmentTime: Int,
        day: Int,
        month: Int,
        year: Int,
        meetingDescription: String,
        paymentAmount: Double, paymentMethod: String
    ): Single<ServerResponse> {
        val param = CreateMeetingRequest(meetingTitle,userId,vendorId,appointmentTime, day, month, year,serviceStatus,bookingStatus,meetingDescription,
            appointmentType, paymentAmount, paymentMethod)
        return profileNetworkService.createMeeting(param)
    }


}