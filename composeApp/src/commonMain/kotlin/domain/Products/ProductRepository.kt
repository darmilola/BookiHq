package domain.Products

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ProductCategoryResponse
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse
import domain.Models.Vendor
import domain.bookings.CreateAppointmentRequest
import kotlinx.serialization.SerialName

interface ProductRepository {
    suspend fun getProductCategories(
        vendorId: Int,
        userId: Int): Single<ProductCategoryResponse>

    suspend fun getProductsInCategory(vendorId: Int, categoryId: Int, nextPage: Int = 1): Single<ListDataResponse<Product>>
    suspend fun searchProducts(vendorId: Int, searchQuery: String, nextPage: Int = 1): Single<ListDataResponse<Product>>
    suspend fun createOrder(vendorId: Int,userId: Int,orderReference: Int,deliveryMethod: String,paymentMethod: String,orderItems: ArrayList<OrderItemRequest>): Single<ServerResponse>
}
