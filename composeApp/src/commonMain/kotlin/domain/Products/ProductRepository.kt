package domain.Products

import com.badoo.reaktive.single.Single
import domain.Enums.ProductType
import domain.Models.FavoriteProductIdResponse
import domain.Models.FavoriteProductResponse
import domain.Models.InitCheckoutResponse
import domain.Models.ProductListDataResponse
import domain.Models.ServerResponse

interface ProductRepository {
    suspend fun getAllProducts(vendorId: Long, nextPage: Int = 1): Single<ProductListDataResponse>
    suspend fun searchProducts(vendorId: Long, searchQuery: String, nextPage: Int = 1): Single<ProductListDataResponse>
    suspend fun createOrder(vendorId: Long, userId: Long, deliveryMethod: String, day: Int, month: Int, year: Int, orderItemJson: String, paymentAmount: Long): Single<ServerResponse>
    suspend fun getProductsByType(vendorId: Long, productType: String = ProductType.COSMETICS.toPath(), nextPage: Int = 1): Single<ProductListDataResponse>
    suspend fun addFavoriteProduct(userId: Long, vendorId: Long, productId: Long): Single<ServerResponse>
    suspend fun removeFavoriteProduct(userId: Long, productId: Long): Single<ServerResponse>
    suspend fun getFavoriteProducts(userId: Long): Single<FavoriteProductResponse>
    suspend fun getFavoriteProductIds(userId: Long): Single<FavoriteProductIdResponse>
}
