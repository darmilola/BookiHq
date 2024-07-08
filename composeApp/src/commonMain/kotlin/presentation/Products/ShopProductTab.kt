package presentation.Products

import GGSansRegular
import StackedSnackbarHost
import StackedSnakbarHostState
import androidx.compose.foundation.BorderStroke
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.CRUD
import domain.Enums.ProductType
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.ProductItemUIModel
import domain.Enums.Screens
import domain.Models.NetworkState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ShopProductsHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.components.ToggleButton
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductResourceListEnvelopeViewModel
import presentation.viewmodels.ProductViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.ProductItem
import presentation.widgets.ProductDetailBottomSheet
import presentation.widgets.SearchBar
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.getPopularProductViewHeight


class ShopProductTab(private val mainViewModel: MainViewModel,
                     private val productViewModel: ProductViewModel) : Tab, KoinComponent {

    private val productPresenter: ProductPresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null
    private var productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel? = null

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Products"
            val icon = painterResource("drawable/shop_icon.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon

                )
            }
        }

    @Composable
    override fun Content() {
        val vendorId = mainViewModel.vendorId.value
        val onCartChanged = remember { mutableStateOf(false) }
        val searchQuery = remember { mutableStateOf("") }
        val isSearchProduct = mainViewModel.isSearchProduct.collectAsState()
        val selectedProductType = remember { mutableStateOf(ProductType.COSMETICS.toPath()) }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (productResourceListEnvelopeViewModel == null) {
            productResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ProductResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
            productPresenter.getProductsByType(vendorId, productType = selectedProductType.value)
        }


        val productHandler = ShopProductsHandler(
            uiStateViewModel!!, productResourceListEnvelopeViewModel!!, productPresenter)
        productHandler.init()


        Scaffold(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Color.White),
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                if (isSearchProduct.value){
                    SearchBar(onValueChange = {
                    if (it.isNotEmpty()) {
                               productResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                               searchQuery.value = it
                               productPresenter.searchProducts(
                                   mainViewModel.vendorId.value,
                                   it
                               )
                           }
               }, onBackPressed = {
                   mainViewModel.setIsSearchProduct(false)
                   productResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                        productPresenter.getProductsByType(vendorId, productType = selectedProductType.value)
               })
              }
                else {
                    ToggleButton(shape = CircleShape,
                        onLeftClicked = {
                            productResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                            selectedProductType.value = ProductType.COSMETICS.toPath()
                            productPresenter.getProductsByType(vendorId, productType = selectedProductType.value)
                        },
                        onRightClicked = {
                            productResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                            selectedProductType.value = ProductType.ACCESSORIES.toPath()
                            productPresenter.getProductsByType(vendorId, productType = selectedProductType.value)
                        },
                        leftText = "Cosmetics",
                        rightText = "Accessories")
                }
            },
            content = {
                val uiState = uiStateViewModel!!.uiStateInfo.collectAsState()
                if (uiState.value.loadingVisible) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (uiState.value.errorOccurred) {
                      val message = uiState.value.errorMessage

                } else if (uiState.value.contentVisible) {
                    ProductContent(
                        productResourceListEnvelopeViewModel = productResourceListEnvelopeViewModel!!,
                        searchQuery = searchQuery.value,
                        vendorId = vendorId,
                        mainViewModel = mainViewModel,
                        onCartChanged = {
                            onCartChanged.value = true
                        }, selectedProductType.value
                    )
                }
            },
            backgroundColor = Color.Transparent,
            floatingActionButton = {
                var cartSize = mainViewModel.unSavedOrderSize.collectAsState()
                val cartContainer = if (cartSize.value > 0) 140 else 0
                Box(
                    modifier = Modifier.size(cartContainer.dp)
                        .padding(bottom = 40.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    AttachShoppingCartImage("drawable/shopping_cart.png", mainViewModel)
                }
            }
        )
    }

    @Composable
    fun AttachShoppingCartImage(iconRes: String, mainViewModel: MainViewModel) {
        val currentOrderSize = mainViewModel.unSavedOrderSize.collectAsState()

        val indicatorModifier = Modifier
            .padding(end = 15.dp, bottom = 20.dp)
            .background(color = Color.Transparent)
            .size(30.dp)
            .clip(CircleShape)
            .background(color = Color(color = 0xFFFF5080))

        Box(
            Modifier
                .clip(CircleShape)
                .size(70.dp)
                .clickable {
                    mainViewModel.setScreenNav(
                        Pair(Screens.MAIN_TAB.toPath(), Screens.CART.toPath())
                    )
                }
                .background(color = Colors.primaryColor),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .size(40.dp)
            ImageComponent(
                imageModifier = modifier,
                imageRes = iconRes,
                colorFilter = ColorFilter.tint(color = Color.White)
            )
            Box(
                modifier = indicatorModifier,
                contentAlignment = Alignment.Center
            ) {
                TextComponent(
                    text = currentOrderSize.value.toString(),
                    fontSize = 17,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }


    @Composable
    fun ProductContent(
        productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel,
        searchQuery: String,
        vendorId: Long,
        mainViewModel: MainViewModel,
        onCartChanged: () -> Unit,
        productType: String
    ) {
        val loadMoreState = productResourceListEnvelopeViewModel.isLoadingMore.collectAsState()
        val productList = productResourceListEnvelopeViewModel.resources.collectAsState()
        val selectedProduct = remember { mutableStateOf(Product()) }
        val totalProductsCount =
            productResourceListEnvelopeViewModel.totalItemCount.collectAsState()
        val displayedProductsCount =
            productResourceListEnvelopeViewModel.displayedItemCount.collectAsState()
        val lastIndex = productList.value.size.minus(1)


        var productUIModel by remember {
            mutableStateOf(
                ProductItemUIModel(
                    selectedProduct.value,
                    productList.value
                )
            )
        }

        if (!loadMoreState.value) {
            productUIModel = productUIModel.copy(selectedProduct = selectedProduct.value,
                productList = productResourceListEnvelopeViewModel.resources.value.map { it2 ->
                    it2.copy(
                        isSelected = it2.productId == selectedProduct.value.vendorId
                    )
                })
        }

        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 70.dp),
            contentAlignment = Alignment.Center
        ) {

                productResourceListEnvelopeViewModel.setIsSearching(false)
                val columnModifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = columnModifier
                ) {
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(color = Color.White),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        var showProductDetailBottomSheet by remember { mutableStateOf(false) }
                        if (showProductDetailBottomSheet) {
                            ProductDetailBottomSheet(
                                mainViewModel,
                                isViewedFromCart = false,
                                OrderItem(itemProduct = selectedProduct.value),
                                onDismiss = { isAddToCart, item ->
                                    if (isAddToCart) {
                                        onCartChanged()
                                    }
                                    showProductDetailBottomSheet = false

                                },
                                onRemoveFromCart = {})
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(
                                getPopularProductViewHeight(productUIModel.productList).dp
                            ),
                            contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            userScrollEnabled = true
                        ) {
                            items(productUIModel.productList.size) { it ->
                                ProductItem(
                                    productUIModel.productList[it],
                                    onProductClickListener = { it2 ->
                                        selectedProduct.value = it2
                                        showProductDetailBottomSheet = true
                                    })

                                if (it == lastIndex && loadMoreState.value) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(60.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IndeterminateCircularProgressBar()
                                    }
                                } else if (it == lastIndex && (displayedProductsCount.value < totalProductsCount.value)) {
                                    val buttonStyle = Modifier
                                        .height(55.dp)
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)

                                    ButtonComponent(
                                        modifier = buttonStyle,
                                        buttonText = "Show More",
                                        borderStroke = BorderStroke(
                                            1.dp,
                                            theme.Colors.primaryColor
                                        ),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                        fontSize = 16,
                                        shape = CircleShape,
                                        textColor = theme.Colors.primaryColor,
                                        style = TextStyle()
                                    ) {

                                        if (searchQuery.isEmpty()) {
                                            if (productResourceListEnvelopeViewModel.nextPageUrl.value.isNotEmpty()) {
                                                productPresenter.getMoreProductsByType(
                                                    vendorId = vendorId,
                                                    nextPage = productResourceListEnvelopeViewModel.currentPage.value + 1,
                                                    productType = productType
                                                )
                                            }
                                        } else {
                                            if (productResourceListEnvelopeViewModel.nextPageUrl.value.isNotEmpty()) {
                                                productPresenter.searchMoreProducts(
                                                    vendorId = vendorId,
                                                    nextPage = productResourceListEnvelopeViewModel.currentPage.value + 1,
                                                    searchQuery = searchQuery
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}
