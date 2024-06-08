package presentation.Products

import domain.Models.FavoriteProduct
import domain.Models.OrderItem
import domain.Models.ProductCategory
import domain.Models.ProductResourceListEnvelope
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

class ProductContract {
    interface View {
        fun showLce(uiState: ScreenUIStates)
        fun showProducts(products: ProductResourceListEnvelope?, isFromSearch: Boolean = false)
        fun onLoadMoreProductStarted()
        fun onLoadMoreProductEnded()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getProducts(vendorId: Int)
        abstract fun getMoreProducts(vendorId: Int, nextPage: Int = 1)
        abstract fun searchProducts(vendorId: Int, searchQuery: String)
        abstract fun searchMoreProducts(vendorId: Int, searchQuery: String, nextPage: Int = 1)

    }
}


class CartContract {
    interface View {
        fun showLce(actionUIStates: ActionUIStates)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun createOrder(orderItemList: MutableList<OrderItem>, vendorId: Int, userId: Int, orderReference: Int, deliveryMethod: String, paymentMethod: String)

    }
}