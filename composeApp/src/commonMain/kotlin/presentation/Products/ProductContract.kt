package presentation.Products

import androidx.compose.runtime.snapshots.SnapshotStateList
import domain.Models.FavoriteProduct
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.ProductCategory
import domain.Models.ProductResourceListEnvelope
import domain.Models.ResourceListEnvelope
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

class CategoryContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showProductCategories(productCategories: List<ProductCategory>, favoriteProducts: List<FavoriteProduct>)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getProductCategory(vendorId: Int, userId: Int)

    }
}



class ProductContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showProducts(products: ProductResourceListEnvelope?, isFromSearch: Boolean = false)
        fun onLoadMoreProductStarted(isSuccess: Boolean = false)
        fun onLoadMoreProductEnded(isSuccess: Boolean = false)
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
        fun showLce(uiState: AsyncUIStates, message: String = "")
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun createOrder(orderItemList: MutableList<OrderItem>, vendorId: Int, userId: Int, orderReference: Int, deliveryMethod: String, paymentMethod: String)

    }
}