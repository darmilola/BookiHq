package domain.connectVendor

import com.badoo.reaktive.single.toSingle
import domain.Models.AuthenticationResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import domain.Models.VendorListDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ConnectVendorNetworkService (private val apiService: HttpClient) {
    suspend fun connectVendor(connectVendorRequest: ConnectVendorRequest) =
        apiService.post {
            url("/user/vendor/connect")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(connectVendorRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun searchVendor(searchVendorRequest: SearchVendorRequest, nextPage: Int = 1) =
        apiService.post {
            url("/profile/vendor/search?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(searchVendorRequest)
        }.body<VendorListDataResponse>().toSingle()

    suspend fun getVendor(getVendorRequest: GetVendorRequest, nextPage: Int = 1) =
        apiService.post {
            url("/profile/vendor/get?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getVendorRequest)
        }.body<VendorListDataResponse>().toSingle()


}