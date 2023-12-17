package screens.Products

import androidx.compose.runtime.mutableStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class ProductTabViewModel: ViewModel() {

    private var pageIndex = 0

    private val _productTabScreenData = mutableStateOf(productTabScreenData())
    val productTabScreenData: ProductTabScreenData?
        get() = _productTabScreenData.value



    private val authScreenOrder: List<ProductScreenScreenEnum> = listOf(
        ProductScreenScreenEnum.DESCRIPTION_SCREEN,
        ProductScreenScreenEnum.REVIEWS_SCREEN)

    private fun productTabScreenData(): ProductTabScreenData {
        return ProductTabScreenData(
            screenType = pageIndex
        )
    }

    fun changeScreen(newPageIndex: Int) {
        pageIndex = newPageIndex
        _productTabScreenData.value = productTabScreenData()
    }

}

enum class ProductScreenScreenEnum {
    DESCRIPTION_SCREEN,
    REVIEWS_SCREEN
}


data class ProductTabScreenData(
    val screenType: Int
)