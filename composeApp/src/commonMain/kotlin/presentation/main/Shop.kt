package presentation.main

import GGSansRegular
import GGSansSemiBold
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.LaunchedEffect
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
import domain.Models.FavoriteProduct
import domain.Models.Product
import domain.Models.ProductCategory
import domain.Models.ProductItemUIModel
import domain.Models.ResourceListEnvelope
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
import presentation.Products.ProductItem
import presentation.Products.ProductPresenter
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
import presentations.components.ImageComponent
import presentations.components.TextComponent
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

        val isUserSearching = remember { mutableStateOf(false) }
        val searchQuery = remember { mutableStateOf("") }

        if (categoryUIStateViewModel == null) {
            categoryUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

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
            categoryPresenter.getProductCategory(currentVendor!!.vendorId!!, currentUser!!.userId!!)
        }

        val categoryUIState = categoryUIStateViewModel!!.uiData.collectAsState()

        val categoryHandler = ShopCategoryHandler(productViewModel!!, categoryUIStateViewModel!!, categoryPresenter)
        categoryHandler.init()

       val productHandler = ShopProductsHandler(productResourceListEnvelopeViewModel!!, productUIStateViewModel!!, productPresenter)
        productHandler.init()


        if (categoryUIState.value.loadingVisible) {
            //Content Loading
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IndeterminateCircularProgressBar()
            }
        } else if (categoryUIState.value.errorOccurred) {

            //Error Occurred display reload

        } else if (categoryUIState.value.contentVisible) {

            productCategories = productViewModel!!.productCategories.value

            Scaffold(
                topBar = {
                    SearchBar( onValueChange = {
                        productResourceListEnvelopeViewModel!!.clearData(mutableListOf<Product>())
                        isUserSearching.value = true
                        if(it.isNotEmpty()) {
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
                            productResourceListEnvelopeViewModel!!, productUIStateViewModel!!,
                            productPresenter, mainViewModel
                        )
                    } else{
                        productResourceListEnvelopeViewModel!!.clearData(mutableListOf<Product>())
                        SearchContent(
                            searchQuery.value,
                            productResourceListEnvelopeViewModel!!, productUIStateViewModel!!,
                            productPresenter, mainViewModel)
                    }
                },
                backgroundColor = Color.White,
                floatingActionButton = {
                    AttachShoppingCartImage("drawable/shopping_cart.png")
                }
            )
        }
    }

        @Composable
        fun AttachShoppingCartImage(iconRes: String) {
            val indicatorModifier = Modifier
                .padding(end = 15.dp, bottom = 20.dp)
                .background(color = Color.Transparent)
                .size(30.dp)
                .clip(CircleShape)
                .background(color = Color(color = 0xFFFF5080))

            Box(
                Modifier
                    .padding(bottom = 70.dp)
                    .clip(CircleShape)
                    .size(70.dp)
                    .clickable {
                        // mainViewModel.setId(3)
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
                        text = "5",
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
        fun ProductContent(productCategories: ArrayList<ProductCategory>,productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
                           uiStateViewModel: UIStateViewModel, productPresenter: ProductPresenter, mainViewModel: MainViewModel) {
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
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TabScreen(productCategories,productResourceListEnvelopeViewModel,
                        uiStateViewModel,productPresenter,mainViewModel)
                }

            }
        }
    }


@Composable
fun SearchContent(searchQuery: String, productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
                   uiStateViewModel: UIStateViewModel, productPresenter: ProductPresenter, mainViewModel: MainViewModel) {

    val connectedVendor: Vendor = mainViewModel.connectedVendor.value
    val loadMoreState = productResourceListEnvelopeViewModel.isLoadingMore.collectAsState()
    val productList = productResourceListEnvelopeViewModel.resources.collectAsState()
    val totalProductsCount = productResourceListEnvelopeViewModel.totalItemCount.collectAsState()
    val displayedProductsCount =
        productResourceListEnvelopeViewModel.displayedItemCount.collectAsState()
    val lastIndex = productList.value.size.minus(1)
    val selectedProduct = remember { mutableStateOf(Product()) }
    val productUIState = uiStateViewModel.uiData.collectAsState()
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
        if (productUIState.value.loadingVisible) {
            productResourceListEnvelopeViewModel.setIsSearching(true)
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IndeterminateCircularProgressBar()
            }
        } else if (productUIState.value.errorOccurred) {
            productResourceListEnvelopeViewModel.setIsSearching(false)
            // Error Occurred display reload
        } else if (productUIState.value.contentVisible) {
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
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        var showProductDetailBottomSheet by remember { mutableStateOf(false) }
                        if (showProductDetailBottomSheet) {
                            ProductDetailBottomSheet(selectedProduct.value) {
                                showProductDetailBottomSheet = false
                            }
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(
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
                                        showProductDetailBottomSheet = true
                                        selectedProduct.value = it2
                                    })
                              }
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
                      uiStateViewModel: UIStateViewModel, productPresenter: ProductPresenter, mainViewModel: MainViewModel) {

            var tabIndex by remember { mutableStateOf(0) }
            val productUIState = uiStateViewModel.uiData.collectAsState()

        LaunchedEffect(Unit, block = {
            if(productCategories.isNotEmpty()){
                productResourceListEnvelopeViewModel.clearData(mutableListOf<Product>())
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
                    contentAlignment = Alignment.Center
                ) {
                    if (productUIState.value.loadingVisible) {
                        //Content Loading
                        Box(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            IndeterminateCircularProgressBar()
                        }
                    } else if (productUIState.value.errorOccurred) {
                        // Error Occurred display reload
                    } else if (productUIState.value.contentVisible) {
                        CategoryScreen(
                            productCategories[tabIndex],
                            productResourceListEnvelopeViewModel,
                            uiStateViewModel, productPresenter, mainViewModel = mainViewModel
                        )
                    }
                }
            }
        }



class ShopCategoryHandler(
    private val productViewModel: ProductViewModel,
    private val uiStateViewModel: UIStateViewModel,
    private val categoryPresenter: CategoryPresenter,
) : CategoryContract.View {
    fun init() {
        categoryPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiStateViewModel.switchState(uiState)
    }
    override fun showProductCategories(
        productCategories: List<ProductCategory>,
        favoriteProducts: List<FavoriteProduct>
    ) {
        productViewModel.setProductCategories(productCategories)
        productViewModel.setFavoriteProducts(favoriteProducts)
    }
}



class ShopProductsHandler(
    private val productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
    private val uiStateViewModel: UIStateViewModel,
    private val productPresenter: ProductPresenter,
) : ProductContract.View {
    fun init() {
        productPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiStateViewModel.switchState(uiState)
    }


    override fun showProducts(products: ResourceListEnvelope<Product>?, isFromSearch: Boolean) {
        if (isFromSearch){
            productResourceListEnvelopeViewModel.setResources(products?.resources)
            products?.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            products?.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
            products?.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
            products?.totalItemCount?.let { productResourceListEnvelopeViewModel.setTotalItemCount(it) }
            products?.displayedItemCount?.let { productResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
       else if (productResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
            val productList = productResourceListEnvelopeViewModel.resources.value
            productList.addAll(products?.resources!!)
            productResourceListEnvelopeViewModel.setResources(productList)
            products.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            products.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
            products.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
            products.totalItemCount?.let { productResourceListEnvelopeViewModel.setTotalItemCount(it) }
            products.displayedItemCount?.let { productResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            productResourceListEnvelopeViewModel.setResources(products?.resources)
            products?.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            products?.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
            products?.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
            products?.totalItemCount?.let { productResourceListEnvelopeViewModel.setTotalItemCount(it) }
            products?.displayedItemCount?.let { productResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun onLoadMoreProductStarted(isSuccess: Boolean) {
        productResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreProductEnded(isSuccess: Boolean) {
        productResourceListEnvelopeViewModel.setLoadingMore(false)
    }

}