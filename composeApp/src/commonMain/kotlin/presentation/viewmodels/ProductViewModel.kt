package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.FavoriteProduct
import domain.Models.HomePageResponse
import domain.Models.ProductCategory
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _productCategories =  savedStateHandle.getStateFlow("productCategories", arrayListOf<ProductCategory>())
    private var _favoriteProducts =  savedStateHandle.getStateFlow("favoriteProducts", arrayListOf<FavoriteProduct>())
    private var _shopUiState =  savedStateHandle.getStateFlow("shopUiState", AsyncUIStates())

    val productCategories: StateFlow<ArrayList<ProductCategory>>
        get() = _productCategories

    val shopUiState: StateFlow<AsyncUIStates>
        get() = _shopUiState

    val favoriteProducts: StateFlow<List<FavoriteProduct>>
        get() = _favoriteProducts

    fun setProductCategories(categories: List<ProductCategory>) {
        savedStateHandle["productCategories"] = categories
    }

    fun setFavoriteProducts(favorites: List<FavoriteProduct>) {
        savedStateHandle["favoriteProducts"] = favorites
    }

    fun setShopUIState(asyncUIStates: AsyncUIStates) {
        savedStateHandle["shopUiState"] = asyncUIStates
    }
}