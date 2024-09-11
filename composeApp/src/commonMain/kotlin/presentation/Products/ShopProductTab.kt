package presentation.Products

import GGSansRegular
import StackedSnackbarHost
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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.Enums.ProductType
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.ProductItemUIModel
import domain.Enums.Screens
import domain.Enums.SharedPreferenceEnum
import drawable.ErrorOccurredWidget
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ShopProductsHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.components.ToggleButton
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductResourceListEnvelopeViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.ProductItem
import presentation.widgets.ProductDetailBottomSheet
import presentation.widgets.SearchBar
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.getProductViewHeight

@Parcelize
class ShopProductTab : Tab, KoinComponent, Parcelable {

    @Transient
    private val productPresenter: ProductPresenter by inject()
    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    val preferenceSettings = Settings()
    @Transient
    private var productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel? = null
    private var selectedProductType: String = preferenceSettings[SharedPreferenceEnum.SELECTED_PRODUCT_TYPE.toPath(),""]
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

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

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }



    @Composable
    override fun Content() {
        val vendorId: Long = preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath(),-1L]
        val onCartChanged = remember { mutableStateOf(false) }
        val searchQuery = remember { mutableStateOf("") }
        val isClickedSearchProduct = mainViewModel!!.clickedSearchProduct.collectAsState()
        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (productResourceListEnvelopeViewModel == null) {
            productResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ProductResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })

        }

        val productHandler = ShopProductsHandler(
            loadingScreenUiStateViewModel!!, productResourceListEnvelopeViewModel!!, productPresenter)
        productHandler.init()

        LaunchedEffect(true) {
            if (productResourceListEnvelopeViewModel!!.resources.value.isEmpty()){
                productResourceListEnvelopeViewModel!!.setResources(mutableListOf())
                productPresenter.getProductsByType(vendorId, productType = selectedProductType)
            }
        }


        Scaffold(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Color.White),
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                if (isClickedSearchProduct.value){
                    SearchBar(onValueChange = {
                    if (it.isNotEmpty()) {
                               productResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                               searchQuery.value = it
                               productPresenter.searchProducts(
                                   mainViewModel!!.vendorId.value,
                                   it
                               )
                           }
               }, onBackPressed = {
                    mainViewModel!!.setIsClickedSearchProduct(false)
                    searchQuery.value = ""
                    productResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                    preferenceSettings[SharedPreferenceEnum.SELECTED_PRODUCT_TYPE.toPath()] = ProductType.COSMETICS.toPath()
                    productPresenter.getProductsByType(vendorId, productType = selectedProductType)
               })
              }
                else {
                    val isRightSelected = selectedProductType == ProductType.ACCESSORIES.toPath()
                    ToggleButton(shape = CircleShape,
                        isRightSelection = isRightSelected,
                        onLeftClicked = {
                            preferenceSettings[SharedPreferenceEnum.SELECTED_PRODUCT_TYPE.toPath()] = ProductType.COSMETICS.toPath()
                            productResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                            selectedProductType = ProductType.COSMETICS.toPath()
                            productPresenter.getProductsByType(vendorId, productType = selectedProductType)
                        },
                        onRightClicked = {
                            preferenceSettings[SharedPreferenceEnum.SELECTED_PRODUCT_TYPE.toPath()] = ProductType.ACCESSORIES.toPath()
                            productResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                            selectedProductType = ProductType.ACCESSORIES.toPath()
                            productPresenter.getProductsByType(vendorId, productType = selectedProductType)
                        },
                        leftText = ProductType.COSMETICS.toPath(),
                        rightText = ProductType.ACCESSORIES.toPath())
                }
            },
            content = {
                var showProductDetailBottomSheet by remember { mutableStateOf(false) }
                val selectedProduct = remember { mutableStateOf(Product()) }

                if (showProductDetailBottomSheet) {
                    mainViewModel!!.showProductBottomSheet(true)
                }
                else{
                    mainViewModel!!.showProductBottomSheet(false)
                }

                if (selectedProduct.value.productId != -1L) {
                    ProductDetailBottomSheet(
                        mainViewModel!!,
                        isViewedFromCart = false,
                        OrderItem(itemProduct = selectedProduct.value),
                        onDismiss = {
                            selectedProduct.value = Product()
                        },
                        onAddToCart = { isAddToCart, item ->
                            if (isAddToCart) {
                                onCartChanged.value
                            }
                            showProductDetailBottomSheet = false
                        })
                }

                val uiState = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()
                if (uiState.value.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                }
                else if (uiState.value.isFailed) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        ErrorOccurredWidget(uiState.value.errorMessage, onRetryClicked = {
                            if (productResourceListEnvelopeViewModel!!.resources.value.isEmpty()){
                                productResourceListEnvelopeViewModel!!.setResources(mutableListOf())
                                productPresenter.getProductsByType(vendorId, productType = selectedProductType)
                            }
                        })
                    }
                }
                else if (uiState.value.isEmpty) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyContentWidget(emptyText = uiState.value.emptyMessage)
                    }
                }
                else if (uiState.value.isSuccess) {
                    ProductContent(
                        productResourceListEnvelopeViewModel = productResourceListEnvelopeViewModel!!,
                        searchQuery = searchQuery.value,
                        vendorId = vendorId,
                        onProductSelected = {
                         selectedProduct.value = it
                         showProductDetailBottomSheet = true

                        },
                        selectedProductType
                    )
                }
            },
            backgroundColor = Color.Transparent,
            floatingActionButton = {
                val cartSize = mainViewModel!!.unSavedOrderSize.collectAsState()
                val cartContainer = if (cartSize.value > 0) 140 else 0
                Box(
                    modifier = Modifier.size(cartContainer.dp)
                        .padding(bottom = 45.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    AttachShoppingCartImage("drawable/shopping_cart.png", mainViewModel!!)
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
                    mainViewModel.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.CART.toPath()))
                }
                .background(color = Colors.darkPrimary),
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
        onProductSelected: (Product) -> Unit,
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
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(
                                getProductViewHeight(productList.value).dp
                            ),
                            contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            userScrollEnabled = true
                        ) {
                            runBlocking {
                            items(productUIModel.productList.size) { it ->
                                ProductItem(
                                    productUIModel.productList[it],
                                    onProductClickListener = { it2 ->
                                        onProductSelected(it2)
                                        selectedProduct.value = it2
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
}
