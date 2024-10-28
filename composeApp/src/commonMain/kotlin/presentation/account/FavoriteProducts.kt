package presentation.account

import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Enums.SharedPreferenceEnum
import domain.Models.FavoriteProductIdModel
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.ProductItemUIModel
import drawable.ErrorOccurredWidget
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.FavoriteProductsHandler
import presentation.Products.ProductPresenter
import presentation.components.IndeterminateCircularProgressBar
import presentation.consultation.rightTopBarItem
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductResourceListEnvelopeViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.ProductDetailBottomSheet
import presentation.widgets.ProductItem
import presentation.widgets.TitleWidget
import rememberStackedSnackbarHostState
import utils.ParcelableScreen
import utils.getProductViewHeight

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class FavoriteProducts() : ParcelableScreen, KoinComponent, Parcelable, ScreenTransition {

    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    val preferenceSettings = Settings()
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null
    @Transient var favoriteProductIdModelList = listOf<FavoriteProductIdModel>()
    @Transient
    private val productPresenter: ProductPresenter by inject()
    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }


    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )


        if (productResourceListEnvelopeViewModel == null) {
            productResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ProductResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }

        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val navigator = LocalNavigator.currentOrThrow
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        val userId = preferenceSettings.getLong(SharedPreferenceEnum.USER_ID.toPath(), -1L)
        val vendorId: Long = preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath(),-1L]
        productPresenter.setMainViewModel(mainViewModel!!)
        val favoriteHandler = FavoriteProductsHandler(
            loadingScreenUiStateViewModel = loadingScreenUiStateViewModel,
            productPresenter = productPresenter,
            onFavoriteProductReady = {
                productResourceListEnvelopeViewModel!!.setResources(it)
            },
            onFavoriteProductIdsReady = {
                mainViewModel!!.setFavoriteProductIds(it)
            })
        favoriteHandler.init()

        LaunchedEffect(true) {
            if (productResourceListEnvelopeViewModel!!.resources.value.isEmpty()){
                productResourceListEnvelopeViewModel!!.setResources(mutableListOf())
                productPresenter.getFavoriteProducts(userId)
            }
        }



        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                FavoriteProductScreenTopBar(onBackPressed = {
                    navigator.pop()
                })
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
                                productPresenter.getFavoriteProducts(userId)
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
                    FavoriteProductContent(
                        productResourceListEnvelopeViewModel = productResourceListEnvelopeViewModel!!,
                        vendorId = vendorId,
                        userId = userId,
                        onProductSelected = {
                            selectedProduct.value = it
                            showProductDetailBottomSheet = true
                        }
                    )
                }
            })
    }


    @Composable
    fun FavoriteProductScreenTopBar(onBackPressed: () -> Unit) {

        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp)
            .height(40.dp)

        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem(onBackPressed = {
                    onBackPressed()
                })
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                FavoriteProductTitle()
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                rightTopBarItem()
            }
        }
    }


    @Composable
    fun FavoriteProductContent(
        productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel,
        onProductSelected: (Product) -> Unit,
        vendorId: Long,
        userId: Long)
      {
        val productList = productResourceListEnvelopeViewModel.resources.collectAsState()
        val selectedProduct = remember { mutableStateOf(Product()) }

        val productUIModel by remember {
            mutableStateOf(
                ProductItemUIModel(
                    selectedProduct.value,
                    productList.value
                )
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 70.dp),
            contentAlignment = Alignment.Center
        ) {
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
                                    },
                                    onFavClicked = {
                                        productPresenter.addFavoriteProduct(userId = userId, vendorId = vendorId, productId = it.productId)
                                    },
                                    onUnFavClicked = {
                                        productList.value.remove(it)
                                        productPresenter.removeFavoriteProduct(userId = userId, productId = it.productId)
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun FavoriteProductTitle(){
        TitleWidget(textColor = theme.styles.Colors.primaryColor, title = "Favorite Products")
    }

    @Composable
    fun leftTopBarItem(onBackPressed:() -> Unit) {
        PageBackNavWidget() {
            onBackPressed()
        }
    }

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }


}
