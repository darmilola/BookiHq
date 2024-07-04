package domain.authentication

import com.badoo.reaktive.single.toSingle
import domain.Models.AuthenticationResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
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
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(completeProfileRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun validateProfile(validateProfileRequest: ValidateProfileRequest) =
        apiService.post {
            url("/auth/user/profile/get")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(validateProfileRequest)
        }.body<AuthenticationResponse>().toSingle()

    suspend fun validateEmail(validateProfileRequest: ValidateProfileRequest) =
        apiService.post {
            url("/auth/user/profile/auth/email")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(validateProfileRequest)
        }.body<AuthenticationResponse>().toSingle()

    suspend fun validatePhone(phoneValidateProfileRequest: PhoneValidateProfileRequest) =
        apiService.post {
            url("/auth/user/profile/auth/phone")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(phoneValidateProfileRequest)
        }.body<AuthenticationResponse>().toSingle()

}