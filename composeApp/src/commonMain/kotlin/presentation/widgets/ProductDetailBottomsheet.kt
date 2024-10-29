package presentation.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
fun ProductDetailBottomSheet(mainViewModel: MainViewModel, isViewOnly: Boolean = false, selectedProduct: OrderItem, onAddToCart: (isAddToCart: Boolean, OrderItem) -> Unit,
                             onDismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false))
    val showBottomSheet = mainViewModel.showProductBottomSheet.collectAsState()
    scope.launch {
        scaffoldState.bottomSheetState.hide()
    }
    if (showBottomSheet.value){
        scope.launch {
           scaffoldState.bottomSheetState.show()
        }
    }
    else{
        scope.launch {
            scaffoldState.bottomSheetState.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = {
            onDismiss()
        },
        sheetState = scaffoldState.bottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color(0xFFF3F3F3),
        dragHandle = {},
    ) {
        ProductDetailContent(mainViewModel,isViewOnly,selectedProduct, onAddToCart = {
            onAddToCart(it,selectedProduct)
        })
    }

}