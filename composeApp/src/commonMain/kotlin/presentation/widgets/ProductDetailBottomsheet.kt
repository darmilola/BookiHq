package presentation.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Models.OrderItem
import kotlinx.coroutines.launch
import presentation.Products.ProductDetailContent
import presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailBottomSheet(mainViewModel: MainViewModel, isViewedFromCart: Boolean = false, selectedProduct: OrderItem, onDismiss: (isAddToCart: Boolean, OrderItem) -> Unit, onRemoveFromCart: (OrderItem) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = {
            onDismiss(false, selectedProduct)
        },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color(0xFFF3F3F3),
        dragHandle = {},
    ) {
        ProductDetailContent(mainViewModel,isViewedFromCart,selectedProduct, onAddToCart = {
            scope
                .launch { modalBottomSheetState.hide() }
                .invokeOnCompletion { it2 ->
                    if (!modalBottomSheetState.isVisible) {
                        onDismiss(it,selectedProduct)
                    }
                }
        }, onRemoveFromCart = {
            scope
                .launch { modalBottomSheetState.partialExpand() }
                .invokeOnCompletion { it2 ->
                    if (!modalBottomSheetState.isVisible) {
                        onRemoveFromCart(it)
                    }
                }
        })
    }
}