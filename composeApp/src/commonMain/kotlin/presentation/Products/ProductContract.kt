package presentation.Products

import domain.Models.OrderItem
import domain.Models.ProductResourceListEnvelope
import UIStates.AppUIStates
import com.badoo.reaktive.single.Single
import domain.Enums.ProductType
import domain.Models.FavoriteProductIdModel
import domain.Models.FavoriteProductIdResponse
import domain.Models.FavoriteProductModel
import domain.Models.FavoriteProductResponse
import domain.Models.PaymentAuthorizationData
import domain.Models.PaymentAuthorizationResult
import domain.Models.PlatformNavigator
import domain.Models.Product
import domain.Models.ServerResponse
import domain.Models.User
import domain.Models.Vendor

class ProductContract {
    interface View {
        fun showLce(appUIStates: AppUIStates)
        fun showProducts(products: ProductResourceListEnvelope?, isFromSearch: Boolean = false, isLoadMore: Boolean = false)
        fun onLoadMoreProductStarted()
        fun onLoadMoreProductEnded()
        fun onProductTypeChangeStarted()
        fun onProductTypeChangeEnded(isSuccess: Boolean = false)
    }

    interface FavoriteProductView {
        fun showLce(appUIStates: AppUIStates)
        fun showFavoriteProducts(favoriteProducts: List<Product>)
        fun showFavoriteProductIds(favoriteProductIds: List<FavoriteProductIdModel>)
        fun onFavoriteProductChanged()
    }

    abstract class Presenter {

        abstract fun getProductsByType(vendorId: Long,productType: String = ProductType.COSMETICS.toPath())
        abstract fun onProductTypeChanged(vendorId: Long,productType: String = ProductType.COSMETICS.toPath())
        abstract fun getMoreProductsByType(vendorId: Long,productType: String = ProductType.COSMETICS.toPath(),nextPage: Int = 1)
        abstract fun registerUIContract(view: View?)
        abstract fun registerFavoriteUIContract(view: FavoriteProductView?)
        abstract fun getProducts(vendorId: Long)
        abstract fun getMoreProducts(vendorId: Long, nextPage: Int = 1)
        abstract fun searchProducts(vendorId: Long, searchQuery: String)
        abstract fun searchMoreProducts(vendorId: Long, searchQuery: String, nextPage: Int = 1)
        abstract fun addFavoriteProduct(userId: Long, vendorId: Long, productId: Long)
        abstract fun removeFavoriteProduct(userId: Long, productId: Long)
        abstract fun getFavoriteProducts(userId: Long)
        abstract fun getFavoriteProductIds(userId: Long)

    }
}


class CartContract {
    interface View {
        fun showLce(appUIStates: AppUIStates)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun createOrder(orderItemList: List<OrderItem>, vendorId: Long, userId: Long, deliveryMethod: String, paymentMethod: String, day: Int, month: Int, year: Int,
                                 user: User, vendor: Vendor,paymentAmount: Long, platformNavigator: PlatformNavigator)

    }
}