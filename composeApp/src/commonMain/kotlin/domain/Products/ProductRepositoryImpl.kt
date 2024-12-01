package domain.Products

import com.badoo.reaktive.single.Single
import domain.Models.FavoriteProductIdResponse
import domain.Models.FavoriteProductResponse
import domain.Models.InitCheckoutResponse
import domain.Models.ProductListDataResponse
import domain.Models.ServerResponse
import io.ktor.client.HttpClient
import kotlinx.serialization.SerialName

class ProductRepositoryImpl(apiService: HttpClient): ProductRepository {

    private val productNetworkService: ProductNetworkService = ProductNetworkService(apiService)
    override suspend fun getAllProducts(
        vendorId: Long,
        nextPage: Int
    ): Single<ProductListDataResponse> {
        val param = GetAllProductsRequest(vendorId = vendorId)
        return productNetworkService.getAllProducts(param,nextPage)
    }


    override suspend fun searchProducts(
        vendorId: Long,
        searchQuery: String,
        nextPage: Int
    ): Single<ProductListDataResponse> {
        val param = SearchProductRequest(vendorId, searchQuery)
        return productNetworkService.searchProduct(param,nextPage)
    }

    override suspend fun createOrder(
        vendorId: Long,
        userId: Long,
        deliveryMethod: String,
        paymentMethod: String,
        day: Int,
        month: Int,
        year: Int,
        orderItemJson: String,
        paymentAmount: Long
    ): Single<ServerResponse> {
        val param = CreateOrderRequest(vendorId, userId, deliveryMethod,day,month,year,paymentAmount,paymentMethod,orderItemJson)
        return productNetworkService.createOrder(param)
    }

    override suspend fun getProductsByType(
        vendorId: Long,
        productType: String,
        nextPage: Int
    ): Single<ProductListDataResponse> {
        val param = GetProductTypeRequest(vendorId, productType)
        return productNetworkService.getProductType(param, nextPage)
    }

    override suspend fun addFavoriteProduct(
        userId: Long,
        vendorId: Long,
        productId: Long
    ): Single<ServerResponse> {
        val param = AddFavoriteProductRequest(userId, productId, vendorId)
        return productNetworkService.addFavoriteProduct(param)
    }

    override suspend fun removeFavoriteProduct(
        userId: Long,
        productId: Long
    ): Single<ServerResponse> {
        val param = RemoveFavoriteProductRequest(userId,productId)
        return productNetworkService.removeFavoriteProduct(param)
    }

    override suspend fun getFavoriteProducts(userId: Long): Single<FavoriteProductResponse> {
        val param = GetFavoriteProductRequest(userId)
        return productNetworkService.getFavoriteProducts(param)
    }

    override suspend fun getFavoriteProductIds(userId: Long): Single<FavoriteProductIdResponse> {
        val param = GetFavoriteProductIdsRequest(userId)
        return productNetworkService.getFavoriteProductIds(param)
    }


}