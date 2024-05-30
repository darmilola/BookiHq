package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.FavoriteProduct
import domain.Models.ProductCategory
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _productCategories =  savedStateHandle.getStateFlow("productCategories", arrayListOf<ProductCategory>())
    private var _favoriteProducts =  savedStateHandle.getStateFlow("favoriteProducts", arrayListOf<FavoriteProduct>())
    private var _categoryUiState =  savedStateHandle.getStateFlow("categoryUiState", AsyncUIStates())
    private var _productUiState =  savedStateHandle.getStateFlow("productUiState", AsyncUIStates())

    val productCategories: StateFlow<ArrayList<ProductCategory>>
        get() = _productCategories

    val categoryUiState: StateFlow<AsyncUIStates>
        get() = _categoryUiState

    val productUiState: StateFlow<AsyncUIStates>
        get() = _productUiState

    val favoriteProducts: StateFlow<List<FavoriteProduct>>
        get() = _favoriteProducts

    fun setProductCategories(categories: List<ProductCategory>) {
        savedStateHandle["productCategories"] = categories
    }

    fun setFavoriteProducts(favorites: List<FavoriteProduct>) {
        savedStateHandle["favoriteProducts"] = favorites
    }

    fun setCategoryUIState(asyncUIStates: AsyncUIStates) {
        savedStateHandle["categoryUiState"] = asyncUIStates
    }

    fun setProductUIState(asyncUIStates: AsyncUIStates) {
        savedStateHandle["productUiState"] = asyncUIStates
    }
}