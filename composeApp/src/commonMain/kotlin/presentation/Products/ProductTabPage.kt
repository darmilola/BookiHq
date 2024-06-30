package presentation.Products

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import domain.Models.Product
import presentation.viewmodels.ProductTabViewModel

@Composable
fun ProductTabScreen(product: Product, currentPosition: Int = 0) {

    val viewModel = ProductTabViewModel()
    viewModel.changeScreen(currentPosition)
    val state = viewModel.productTabScreenData!!.screenType

    AnimatedContent(targetState = state) { targetState ->
        when (targetState) {
            0 -> {
                ProductDescription(product)
            }
            1 -> {
                if (product.productReviews!!.isNotEmpty()) {
                    AttachProductReviews(product)
                }
            }
        }
    }

}
