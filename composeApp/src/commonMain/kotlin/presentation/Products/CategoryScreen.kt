package presentation.Products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Models.Product
import domain.Models.ProductCategory
import domain.Models.Vendor
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel

@Composable
fun CategoryScreen(productCategory: ProductCategory,
                   productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
                   uiStateViewModel: UIStateViewModel, productPresenter: ProductPresenter, mainViewModel: MainViewModel) {

    productResourceListEnvelopeViewModel.clearData()

    val connectedVendor: Vendor = mainViewModel.connectedVendor.value
    val loadMoreState = productResourceListEnvelopeViewModel.isLoadingMore.collectAsState()
    val productList = productResourceListEnvelopeViewModel.resources.collectAsState()
    val totalProductsCount = productResourceListEnvelopeViewModel.totalItemCount.collectAsState()
    val displayedProductsCount = productResourceListEnvelopeViewModel.displayedItemCount.collectAsState()
    val lastIndex = productList.value.size.minus(1)


        Column {
            var showProductDetailBottomSheet by remember { mutableStateOf(false) }
            if (showProductDetailBottomSheet) {
                ProductDetailBottomSheet() {
                    showProductDetailBottomSheet = false
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).fillMaxHeight(),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(10) {
                    ProductItem(onProductClickListener = {
                        showProductDetailBottomSheet = true
                    })
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailBottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color(0xFFF3F3F3),
        dragHandle = { },
    ) {
       ProductDetailContent()
    }
}
