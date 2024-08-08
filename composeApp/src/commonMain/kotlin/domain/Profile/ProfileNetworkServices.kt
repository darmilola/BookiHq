package domain.Profile

import com.badoo.reaktive.single.toSingle
import domain.Models.PlatformCountryCitiesResponse
import domain.Models.ServerResponse
import domain.Models.VendorAccountResponse
import domain.Models.VendorAvailabilityResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class ProfileNetworkService(private val apiService: HttpClient) {

    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest) =
        apiService.post {
            url("/auth/user/profile/update")
            contentType(ContentType.Application.Json)
            setBody(updateProfileRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun deleteProfile(deleteProfileRequest: DeleteProfileRequest) =
        apiService.post {
            url("/auth/user/profile/delete")
            contentType(ContentType.Application.Json)
            setBody(deleteProfileRequest)
        }.body<ServerResponse>().toSingle()


    suspend fun getVendorAvailableTimes(getVendorAvailabilityRequest: GetVendorAvailabilityRequest) =
        apiService.post {
            url("/profile/vendor/availability/get")
            contentType(ContentType.Application.Json)
            setBody(getVendorAvailabilityRequest)
        }.body<VendorAvailabilityResponse>().toSingle()

    suspend fun getPlatformCities(getPlatformCitiesRequest: GetPlatformCitiesRequest) =
        apiService.post {
            url("/platform/city/get")
            contentType(ContentType.Application.Json)
            setBody(getPlatformCitiesRequest)
        }.body<PlatformCountryCitiesResponse>().toSingle()

    suspend fun createMeeting(createMeetingRequest: CreateMeetingRequest) =
        apiService.post {
            url("/appointment/meeting/create")
            contentType(ContentType.Application.Json)
            setBody(createMeetingRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun switchVendor(switchVendorRequest: SwitchVendorRequest) =
        apiService.post {
            url("/profile/vendor/switch")
            contentType(ContentType.Application.Json)
            setBody(switchVendorRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun getVendorInfo(getVendorInfoRequest: GetVendorInfoRequest) =
        apiService.post {
            url("/vendor/account/get")
            contentType(ContentType.Application.Json)
            setBody(getVendorInfoRequest)
        }.body<VendorAccountResponse>().toSingle()

    suspend fun joinSpa(joinSpaRequest: JoinSpaRequest) =
        apiService.post {
            url("/vendor/therapist/add")
            contentType(ContentType.Application.Json)
            setBody(joinSpaRequest)
        }.body<ServerResponse>().toSingle()

}