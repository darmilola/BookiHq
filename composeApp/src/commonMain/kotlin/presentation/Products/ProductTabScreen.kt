package presentation.Products

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import domain.Models.Product
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.viewmodels.ProductTabViewModel

@Composable
fun ProductTabScreen(product: Product, currentPosition: Int = 0) {

    val viewModel: ProductTabViewModel = ProductTabViewModel()
    viewModel.changeScreen(currentPosition)
    val productTabScreenData = viewModel.productTabScreenData ?: return
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
