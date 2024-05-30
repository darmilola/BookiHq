package presentation.main

import GGSansRegular
import GGSansSemiBold
import StackedSnackbarHost
import StackedSnakbarHostState
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
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.FavoriteProduct
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.ProductCategory
import domain.Models.ProductItemUIModel
import domain.Models.ResourceListEnvelope
import domain.Models.Screens
import domain.Models.User
import domain.Models.Vendor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.Products.SearchBar
import presentation.Products.CategoryScreen
import presentation.Products.CategoryContract
import presentation.Products.CategoryPresenter
import presentation.Products.HomeProductItem
import presentation.Products.ProductContract
import presentation.Products.ProductDetailBottomSheet
import presentation.Products.ProductPresenter
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.getPopularProductViewHeight


class ShopTab(private val mainViewModel: MainViewModel) : Tab, KoinComponent {

    private val categoryPresenter: CategoryPresenter by inject()
    private val productPresenter: ProductPresenter by inject()
    private var categoryUIStateViewModel: UIStateViewModel? = null
    private var productUIStateViewModel: UIStateViewModel? = null
    private var productViewModel: ProductViewModel? = null
    private var currentUser: User? = null
    private var currentVendor: Vendor?  = null
    private var productCategories: ArrayList<ProductCategory>? = null
    private var productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>? = null

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
        currentUser = mainViewModel.currentUserInfo.value
        currentVendor = mainViewModel.connectedVendor.value

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (productUIStateViewModel == null) {
            productUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (productResourceListEnvelopeViewModel == null) {
            productResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }

        if (productViewModel == null) {
            productViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ProductViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        if (categoryUIStateViewModel == null) {
            categoryUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
            val categoryHandler = ShopCategoryHandler(categoryUIStateViewModel!!,
                onPageLoading = {
                    productViewModel!!.setCategoryUIState(AsyncUIStates(isLoading = true))

                },
                onProductCategoryAvailable = {
                productCategories, favoriteProducts ->
                     productViewModel!!.setProductCategories(productCategories)
                     productViewModel!!.setFavoriteProducts(favoriteProducts)
                     productViewModel!!.setCategoryUIState(AsyncUIStates(isSuccess = true, isDone = true))
                 },
                onErrorVisible = {
                  productViewModel!!.setCategoryUIState(AsyncUIStates(isSuccess = false, isDone = true))
                }, categoryPresenter)
               categoryHandler.init()
         }


            val productHandler = ShopProductsHandler(
                productUIStateViewModel!!, productPresenter,
                onPageLoading = {
                    productViewModel!!.setProductUIState(AsyncUIStates(isLoading = true))
                },
                onProductAvailable = {
                    products: ResourceListEnvelope<Product>?, isFromSearch: Boolean ->
                    productViewModel!!.setProductUIState(AsyncUIStates(isDone = true, isSuccess = true))
                 if (isFromSearch){
                    productResourceListEnvelopeViewModel!!.setResources(products?.resources)
                    products?.prevPageUrl?.let { productResourceListEnvelopeViewModel!!.setPrevPageUrl(it) }
                    products?.nextPageUrl?.let { productResourceListEnvelopeViewModel!!.setNextPageUrl(it) }
                    products?.currentPage?.let { productResourceListEnvelopeViewModel!!.setCurrentPage(it) }
                    products?.totalItemCount?.let { productResourceListEnvelopeViewModel!!.setTotalItemCount(it) }
                    products?.displayedItemCount?.let { productResourceListEnvelopeViewModel!!.setDisplayedItemCount(it) }
                }
                else if (productResourceListEnvelopeViewModel!!.resources.value.isNotEmpty()) {
                    val productList = productResourceListEnvelopeViewModel!!.resources.value
                    productList.addAll(products?.resources!!)
                    productResourceListEnvelopeViewModel!!.setResources(productList)
                    products.prevPageUrl?.let { productResourceListEnvelopeViewModel!!.setPrevPageUrl(it) }
                    products.nextPageUrl?.let { productResourceListEnvelopeViewModel!!.setNextPageUrl(it) }
                    products.currentPage?.let { productResourceListEnvelopeViewModel!!.setCurrentPage(it) }
                    products.totalItemCount?.let { productResourceListEnvelopeViewModel!!.setTotalItemCount(it) }
                    products.displayedItemCount?.let { productResourceListEnvelopeViewModel!!.setDisplayedItemCount(it) }
                } else {
                    productResourceListEnvelopeViewModel!!.setResources(products?.resources)
                    products?.prevPageUrl?.let { productResourceListEnvelopeViewModel!!.setPrevPageUrl(it) }
                    products?.nextPageUrl?.let { productResourceListEnvelopeViewModel!!.setNextPageUrl(it) }
                    products?.currentPage?.let { productResourceListEnvelopeViewModel!!.setCurrentPage(it) }
                    products?.totalItemCount?.let { productResourceListEnvelopeViewModel!!.setTotalItemCount(it) }
                    products?.displayedItemCount?.let { productResourceListEnvelopeViewModel!!.setDisplayedItemCount(it) }
                }
                }, onErrorVisible = {
                    productViewModel!!.setProductUIState(AsyncUIStates(isSuccess = false, isDone = true))
                }, onLoadMoreProduct = {
                    isLoadMore -> productResourceListEnvelopeViewModel!!.setLoadingMore(isLoadMore)
                })
              productHandler.init()
              categoryPresenter.getProductCategory(currentVendor!!.vendorId!!, currentUser!!.userId!!)
        }


        val categoryUIState = productViewModel!!.categoryUiState.collectAsState()
        val productUIState = productViewModel!!.productUiState.collectAsState()
        val isUserSearching = remember { mutableStateOf(false) }
        val searchQuery = remember { mutableStateOf("") }


        if (categoryUIState.value.isLoading) {

            // Product Category is Loading

            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IndeterminateCircularProgressBar()
            }
        } else if (categoryUIState.value.isDone && !categoryUIState.value.isSuccess) {

            // Error Occurred display reload

        } else if (categoryUIState.value.isDone && categoryUIState.value.isSuccess) {

            productCategories = productViewModel!!.productCategories.value

                 Scaffold(
                    snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
                    topBar = {
                        SearchBar(onValueChange = {
                            productResourceListEnvelopeViewModel!!.clearData(mutableListOf<Product>())
                            isUserSearching.value = true
                            if (it.isNotEmpty()) {
                                searchQuery.value = it
                                if (!productResourceListEnvelopeViewModel!!.isSearching.value) {
                                    productPresenter.searchProducts(
                                        mainViewModel.connectedVendor.value.vendorId!!,
                                        it
                                    )
                                }
                            }
                        }, onBackPressed = {
                            productResourceListEnvelopeViewModel!!.clearData(mutableListOf<Product>())
                            isUserSearching.value = false
                        })
                    },
                    content = {
                        if (!isUserSearching.value) {
                            productResourceListEnvelopeViewModel!!.clearData(mutableListOf<Product>())
                            ProductContent(
                                productCategories!!,
                                productResourceListEnvelopeViewModel!!, productViewModel!!,
                                productPresenter, mainViewModel,stackedSnackBarHostState
                            )
                        } else {
                            productResourceListEnvelopeViewModel!!.clearData(mutableListOf<Product>())
                            SearchContent(productResourceListEnvelopeViewModel!!, productViewModel!!, mainViewModel,stackedSnackBarHostState)
                        }
                    },
                    backgroundColor = Color.White,
                    floatingActionButton = {

                        AttachShoppingCartImage("drawable/shopping_cart.png",mainViewModel)
                    }
                )
            }
    }

        @Composable
        fun AttachShoppingCartImage(iconRes: String, mainViewModel: MainViewModel) {
            val currentOrders = mainViewModel.unSavedOrders.collectAsState()
            val cartSize = currentOrders.value
            val indicatorModifier = Modifier
                .padding(end = 15.dp, bottom = 20.dp)
                .background(color = Color.Transparent)
                .size(30.dp)
                .clip(CircleShape)
                .background(color = Color(color = 0xFFFF5080))

        if (cartSize.size > 0) {

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
                            text = cartSize.size.toString(),
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
        }


        @Composable
        fun ProductContent(productCategories: ArrayList<ProductCategory>,productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
                           productViewModel: ProductViewModel, productPresenter: ProductPresenter, mainViewModel: MainViewModel,stackedSnackBarHostState: StackedSnakbarHostState) {
            val columnModifier = Modifier
                .padding(top = 5.dp)
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
                    TabScreen(productCategories,productResourceListEnvelopeViewModel,
                        productViewModel,productPresenter,mainViewModel,stackedSnackBarHostState)
                }

            }
        }
    }


@Composable
fun SearchContent(productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
                  productViewModel: ProductViewModel, mainViewModel: MainViewModel, stackedSnackBarHostState: StackedSnakbarHostState) {
    val loadMoreState = productResourceListEnvelopeViewModel.isLoadingMore.collectAsState()
    val productList = productResourceListEnvelopeViewModel.resources.collectAsState()
    val selectedProduct = remember { mutableStateOf(Product()) }
    val productUIState = productViewModel.productUiState.collectAsState()
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
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        if (productUIState.value.isLoading) {
            productResourceListEnvelopeViewModel.setIsSearching(true)
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IndeterminateCircularProgressBar()
            }
        } else if (productUIState.value.isDone && !productUIState.value.isSuccess) {
            productResourceListEnvelopeViewModel.setIsSearching(false)
            // Error Occurred display reload
        } else if (productUIState.value.isDone && productUIState.value.isSuccess) {
            productResourceListEnvelopeViewModel.setIsSearching(false)
            val columnModifier = Modifier
                .padding(top = 5.dp)
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
                            ProductDetailBottomSheet(mainViewModel,isViewedFromCart = false, OrderItem(itemProduct = selectedProduct.value),onDismiss = {
                            isAddToCart,item -> if (isAddToCart){
                                ShowSnackBar(title = "Successful",
                                    description = "Your Product has been successfully Added to Cart",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Short,
                                    snackBarType = SnackBarType.SUCCESS,
                                    stackedSnackBarHostState,
                                    onActionClick = {})
                                   }
                                showProductDetailBottomSheet = false

                            },onRemoveFromCart = {})
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
                                HomeProductItem(
                                    productUIModel.productList[it],
                                    onProductClickListener = { it2 ->
                                        selectedProduct.value = it2
                                        showProductDetailBottomSheet = true
                                    })
                              }
                        }
                }

            }
        }
    }
}

        @Composable
        fun TabScreen(productCategories: ArrayList<ProductCategory>,
                      productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
                      productViewModel: ProductViewModel, productPresenter: ProductPresenter, mainViewModel: MainViewModel,stackedSnackBarHostState: StackedSnakbarHostState) {

            var tabIndex by remember { mutableStateOf(0) }
            val productUIState = productViewModel.productUiState.collectAsState()

        LaunchedEffect(Unit, block = {
            if(productCategories.isNotEmpty()){
                productResourceListEnvelopeViewModel.clearData(mutableListOf())
                productPresenter.getProducts(mainViewModel.connectedVendor.value.vendorId!!, productCategories[tabIndex].categoryId!!)
               }
            })

            Column(modifier = Modifier.fillMaxSize().padding(top = 10.dp)) {
                ScrollableTabRow(selectedTabIndex = tabIndex,
                    modifier = Modifier.height(40.dp),
                    backgroundColor = Color.Transparent,
                    indicator = { tabPositions ->
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[tabIndex])
                                .height(3.dp)
                                .padding(start = 30.dp, end = 30.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color = Colors.primaryColor)
                        )

                    },
                    divider = {}) {
                    productCategories.forEachIndexed { index, title ->
                        Tab(text =
                        {
                            TextComponent(
                                text = productCategories[index].categoryTitle.toString(),
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = MaterialTheme.typography.h6,
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 30
                            )
                        },
                            selected = tabIndex == index,
                            onClick = {
                                tabIndex = index
                                productResourceListEnvelopeViewModel.clearData(mutableListOf<Product>())
                                productPresenter.getProducts(mainViewModel.connectedVendor.value.vendorId!!, productCategories[tabIndex].categoryId!!)
                            }

                        )
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    contentAlignment = Alignment.TopStart
                ) {
                    if (productUIState.value.isLoading) {
                        //Content Loading
                        Box(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            IndeterminateCircularProgressBar()
                        }
                    } else if (productUIState.value.isDone && !productUIState.value.isSuccess) {
                        // Error Occurred display reload


                    } else if (productUIState.value.isDone && productUIState.value.isSuccess) {

                        CategoryScreen(
                            productCategories[tabIndex],
                            productResourceListEnvelopeViewModel, productPresenter, mainViewModel = mainViewModel,stackedSnackBarHostState
                        )
                    }
                }
            }
        }



class ShopCategoryHandler(
    private val uiStateViewModel: UIStateViewModel,
    private val onPageLoading: () -> Unit,
    private val onProductCategoryAvailable: (productCategories: List<ProductCategory>, favoriteProducts: List<FavoriteProduct>) -> Unit,
    private val onErrorVisible: () -> Unit,
    private val categoryPresenter: CategoryPresenter
) : CategoryContract.View {
    fun init() {
        categoryPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiStateViewModel.switchState(uiState)
        uiState.let {
            when {
                it.loadingVisible -> {
                    onPageLoading()
                }
                it.errorOccurred -> {
                    onErrorVisible()
                }
            }
        }
    }
    override fun showProductCategories(
        productCategories: List<ProductCategory>,
        favoriteProducts: List<FavoriteProduct>
    ) {
        onProductCategoryAvailable(productCategories, favoriteProducts)
    }
}



class ShopProductsHandler(
    private val uiStateViewModel: UIStateViewModel,
    private val productPresenter: ProductPresenter,
    private val onPageLoading: () -> Unit,
    private val onProductAvailable: (products: ResourceListEnvelope<Product>?, isFromSearch: Boolean) -> Unit,
    private val onLoadMoreProduct: (isLoadMore: Boolean) -> Unit,
    private val onErrorVisible: () -> Unit
) : ProductContract.View {
    fun init() {
        productPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiStateViewModel.switchState(uiState)
        uiState.let {
            when {
                it.loadingVisible -> {
                    onPageLoading()
                }
                it.errorOccurred -> {
                    onErrorVisible()
                }
            }
        }
    }

    override fun showProducts(products: ResourceListEnvelope<Product>?, isFromSearch: Boolean) {
        onProductAvailable(products, isFromSearch)
    }

    override fun onLoadMoreProductStarted(isSuccess: Boolean) {
        onLoadMoreProduct(isSuccess)
    }

    override fun onLoadMoreProductEnded(isSuccess: Boolean) {
        onLoadMoreProduct(isSuccess)
    }

}