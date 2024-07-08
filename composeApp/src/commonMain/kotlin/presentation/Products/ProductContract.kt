package presentation.Products

import domain.Models.OrderItem
import domain.Models.ProductResourceListEnvelope
import UIStates.ActionUIStates
import UIStates.ScreenUIStates

class ProductContract {
    interface View {
        fun showLce(uiState: ScreenUIStates)
        fun showProducts(products: ProductResourceListEnvelope?)
        fun showSearchProducts(products: ProductResourceListEnvelope?, isLoadMore: Boolean = false)
        fun onLoadMoreProductStarted()
        fun onLoadMoreProductEnded()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getProducts(vendorId: Long)
        abstract fun getMoreProducts(vendorId: Long, nextPage: Int = 1)
        abstract fun searchProducts(vendorId: Long, searchQuery: String)
        abstract fun searchMoreProducts(vendorId: Long, searchQuery: String, nextPage: Int = 1)

    }
}


class CartContract {
    interface View {
        fun showLce(actionUIStates: ActionUIStates)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun createOrder(orderItemList: MutableList<OrderItem>, vendorId: Long, userId: Long, orderReference: Int, deliveryMethod: String, paymentMethod: String)

    }
}