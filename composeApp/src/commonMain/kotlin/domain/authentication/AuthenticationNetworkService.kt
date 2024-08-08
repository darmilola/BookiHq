package domain.authentication

import com.badoo.reaktive.single.toSingle
import domain.Models.AuthenticationResponse
import domain.Models.CompleteProfileResponse
import domain.Models.ServerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType


open class AuthenticationNetworkService(private val apiService: HttpClient) {
    suspend fun completeProfile(completeProfileRequest: CompleteProfileRequest) =
        apiService.post {
            url("/auth/user/profile/complete")
            contentType(ContentType.Application.Json)
            setBody(completeProfileRequest)
        }.body<CompleteProfileResponse>().toSingle()
    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest) =
        apiService.post {
            url("/user/profile/update")
            contentType(ContentType.Application.Json)
            setBody(updateProfileRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun updateFcmToken(updateFcmRequest: UpdateFcmRequest) =
        apiService.post {
            url("/user/profile/fcm/update")
            contentType(ContentType.Application.Json)
            setBody(updateFcmRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun validateProfile(validateProfileRequest: ValidateProfileRequest) =
        apiService.post {
            url("/user/profile/get")
            contentType(ContentType.Application.Json)
            setBody(validateProfileRequest)
        }.body<AuthenticationResponse>().toSingle()

    suspend fun validateEmail(validateProfileRequest: ValidateProfileRequest) =
        apiService.post {
            url("/auth/user/email")
            contentType(ContentType.Application.Json)
            setBody(validateProfileRequest)
        }.body<AuthenticationResponse>().toSingle()

    suspend fun validatePhone(phoneValidateProfileRequest: PhoneValidateProfileRequest) =
        apiService.post {
            url("/auth/user/phone")
            contentType(ContentType.Application.Json)
            setBody(phoneValidateProfileRequest)
        }.body<AuthenticationResponse>().toSingle()

}