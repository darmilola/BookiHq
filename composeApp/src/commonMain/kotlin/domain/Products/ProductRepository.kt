package domain.Products

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ProductCategoryResponse
import domain.Models.ProductListDataResponse
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse
import domain.Models.Vendor
import domain.bookings.CreateAppointmentRequest
import kotlinx.serialization.SerialName

interface ProductRepository {
    suspend fun getAllProducts(vendorId: Int, nextPage: Int = 1): Single<ProductListDataResponse>
    suspend fun searchProducts(vendorId: Int, searchQuery: String, nextPage: Int = 1): Single<ProductListDataResponse>
    suspend fun createOrder(vendorId: Int,userId: Int,orderReference: Int,deliveryMethod: String,paymentMethod: String,orderItems: ArrayList<OrderItemRequest>): Single<ServerResponse>
}
