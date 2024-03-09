package infrastructure.authentication

import com.badoo.reaktive.single.toSingle
import domain.Models.AuthenticationResponse
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
            url("/api/v1/auth/user/profile/complete")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(completeProfileRequest)
        }.body<ServerResponse>().toSingle()


    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest) =
        apiService.post {
            url("/api/v1/auth/user/profile/update")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(updateProfileRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun deleteProfile(deleteProfileRequest: DeleteProfileRequest) =
        apiService.post {
            url("/api/v1/auth/user/profile/delete")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(deleteProfileRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun getProfile(getProfileRequest: GetProfileRequest) =
        apiService.post {
            url("/api/v1/auth/user/profile/get")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getProfileRequest)
        }.body<AuthenticationResponse>().toSingle()

    suspend fun connectVendor(connectVendorRequest: ConnectVendorRequest) =
        apiService.post {
            url("/api/v1/auth/user/vendor/connect")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(connectVendorRequest)
        }.body<ServerResponse>().toSingle()

}