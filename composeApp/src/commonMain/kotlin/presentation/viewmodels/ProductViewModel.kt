package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.FavoriteProduct
import domain.Models.ProductCategory
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {}