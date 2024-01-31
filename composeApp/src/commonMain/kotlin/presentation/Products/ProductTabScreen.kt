package presentation.Products

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.viewmodels.ProductTabViewModel


@OptIn(ExperimentalResourceApi::class, ExperimentalAnimationApi::class)
@Composable
fun ProductTabScreen(currentPosition: Int = 0) {

    val viewModel: ProductTabViewModel = ProductTabViewModel()
    viewModel.changeScreen(currentPosition)
    val productTabScreenData = viewModel.productTabScreenData ?: return
    val state = viewModel.productTabScreenData!!.screenType


    AnimatedContent(targetState = state) { targetState ->
        // It's important to use targetState and not state, as its critical to ensure
        // a successful lookup of all the incoming and outgoing content during
        // content transform.
        when (targetState) {
            0 -> {
                ProductDescription()
            }
            1 -> {
                AttachProductReviews()
            }
        }
    }



}

class ProductTabComposeScreen(currentScreen: Int = 0) : Screen {

    private val sc = currentScreen
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        ProductTabScreen(currentPosition = sc)
    }
}

