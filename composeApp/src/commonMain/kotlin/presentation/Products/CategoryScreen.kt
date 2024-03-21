package presentation.Products

import StackedSnakbarHostState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.ProductCategory
import domain.Models.ProductItemUIModel
import domain.Models.Vendor
import domain.Models.VendorItemUIModel
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import theme.Colors
import utils.getPopularProductViewHeight

@Composable
fun CategoryScreen(productCategory: ProductCategory,
                   productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
                   uiStateViewModel: UIStateViewModel, productPresenter: ProductPresenter, mainViewModel: MainViewModel,stackedSnackBarHostState: StackedSnakbarHostState) {

    val connectedVendor: Vendor = mainViewModel.connectedVendor.value
    val loadMoreState = productResourceListEnvelopeViewModel.isLoadingMore.collectAsState()
    val productList = productResourceListEnvelopeViewModel.resources.collectAsState()
    val totalProductsCount = productResourceListEnvelopeViewModel.totalItemCount.collectAsState()
    val displayedProductsCount = productResourceListEnvelopeViewModel.displayedItemCount.collectAsState()
    val lastIndex = productList.value.size.minus(1)
    val selectedProduct = remember { mutableStateOf(Product()) }
    var productUIModel by remember { mutableStateOf(ProductItemUIModel(selectedProduct.value, productList.value)) }

    if(!loadMoreState.value) {
        productUIModel = productUIModel.copy(selectedProduct = selectedProduct.value,
            productList = productResourceListEnvelopeViewModel.resources.value.map { it2 ->
                it2.copy(
                    isSelected = it2.productId == selectedProduct.value.productId
                )
            })
       }

    var showProductDetailBottomSheet by remember { mutableStateOf(false) }

                if (showProductDetailBottomSheet) {
                    ProductDetailBottomSheet(mainViewModel,isViewedFromCart = false, OrderItem(itemProduct = selectedProduct.value), onDismiss = {
                            isAddToCart, item -> if (isAddToCart){
                            ShowSnackBar(title = "Successful",
                            description = "Your Product has been successfully Added to Cart",
                            actionLabel = "",
                            duration = StackedSnackbarDuration.Short,
                            snackBarType = SnackBarType.SUCCESS,
                            stackedSnackBarHostState,
                            onActionClick = {})
                    }
                        showProductDetailBottomSheet = false

                    }, onRemoveFromCart = {})
                }




            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(
                    getPopularProductViewHeight(productUIModel.productList).dp),
                contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                userScrollEnabled = true
            ) {
                items(productUIModel.productList.size) { it ->
                    HomeProductItem(productUIModel.productList[it],onProductClickListener = { it2 ->
                        showProductDetailBottomSheet = true
                        selectedProduct.value = it2
                    })
                    if (it == lastIndex && loadMoreState.value) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(60.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            IndeterminateCircularProgressBar()
                        }
                    }
                    else if (it == lastIndex && (displayedProductsCount.value < totalProductsCount.value)) {
                        val buttonStyle = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp)

                        ButtonComponent(
                            modifier = buttonStyle,
                            buttonText = "Show More",
                            borderStroke = BorderStroke(1.dp, Colors.primaryColor),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            fontSize = 16,
                            shape = CircleShape,
                            textColor = Colors.primaryColor,
                            style = TextStyle()
                        ) {
                            if (productResourceListEnvelopeViewModel.nextPageUrl.value.isNotEmpty()) {
                                productPresenter.getMoreProducts(connectedVendor.vendorId!!, categoryId = productCategory.categoryId!!,
                                    nextPage = productResourceListEnvelopeViewModel.currentPage.value + 1)
                            }
                        }
                    }
                }
            }
         }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailBottomSheet(mainViewModel: MainViewModel, isViewedFromCart: Boolean = false, cartItem: OrderItem, onDismiss: (isAddToCart: Boolean, OrderItem) -> Unit, onRemoveFromCart: (OrderItem) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var mutableCartItem = cartItem
    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = { onDismiss(false, mutableCartItem) },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color(0xFFF3F3F3),
        dragHandle = {},
    ) {
       ProductDetailContent(mainViewModel,isViewedFromCart,cartItem, onAddToCart = {
           onDismiss(it,cartItem)
       }, onRemoveFromCart = {
           onRemoveFromCart(it)
       })
    }
}
