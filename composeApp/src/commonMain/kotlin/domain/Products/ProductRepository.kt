package domain.Products

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ProductCategoryResponse
import domain.Models.ServiceSpecialistsResponse
import domain.Models.Vendor

interface ProductRepository {
    suspend fun getProductCategories(
        vendorId: Int,
        userId: Int): Single<ProductCategoryResponse>

    suspend fun getProductsInCategory(vendorId: Int, categoryId: Int, nextPage: Int = 1): Single<ListDataResponse<Product>>
    suspend fun searchProducts(vendorId: Int, searchQuery: String, nextPage: Int = 1): Single<ListDataResponse<Product>>
}