package presentation.Products

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import domain.Models.Product
import presentation.viewmodels.ProductTabViewModel
import presentation.widgets.EmptyContentWidget

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
                else{ Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            EmptyContentWidget(emptyText = "No Reviews")
                        }

                }
            }
        }
    }

}
