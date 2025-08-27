package presentation.Screens

import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.TextStyle
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
import com.assignment.moniepointtest.ui.theme.AppTheme
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Enums.RecommendationType
import domain.Enums.SharedPreferenceEnum
import domain.Models.OrderItem
import domain.Models.PlatformNavigator
import domain.Models.Product
import domain.Models.VendorRecommendationItemUIModel
import domain.Models.getVendorRecommendationListItemViewHeight
import presentation.widgets.ErrorOccurredWidget
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.RecommendationsHandler
import presentation.appointmentBookings.BookingScreen
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.home.HomepagePresenter
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.RecommendationsResourceListEnvelopeViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.ProductDetailBottomSheet
import presentation.widgets.SubtitleTextWidget
import presentation.widgets.TitleWidget
import presentation.widgets.VendorRecommendationsItem
import rememberStackedSnackbarHostState
import theme.Colors
import utils.ParcelableScreen

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class RecommendationsScreen(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent,
    ScreenTransition {

    @Transient
    private val preferenceSettings: Settings = Settings()
    @Transient
    private val homepagePresenter: HomepagePresenter by inject()
    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var recommendationsResourceListEnvelopeViewModel: RecommendationsResourceListEnvelopeViewModel? = null
    @Transient private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        val vendorId: Long = preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath(), -1L]
        val navigator = LocalNavigator.currentOrThrow

        if (onBackPressed.value) {
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        if (recommendationsResourceListEnvelopeViewModel == null) {
            recommendationsResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    RecommendationsResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }


        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val handler = RecommendationsHandler(
            recommendationsResourceListEnvelopeViewModel!!,
            loadingScreenUiStateViewModel!!,
            homepagePresenter!!
        )
        handler.init()

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        val recommendationUIModel = remember { mutableStateOf(VendorRecommendationItemUIModel()) }

        val loadMoreState =
            recommendationsResourceListEnvelopeViewModel?.isLoadingMore?.collectAsState()
        val recommendationsList =
            recommendationsResourceListEnvelopeViewModel?.resources?.collectAsState()
        val totalItemCount =
            recommendationsResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
        val displayedItemCount =
            recommendationsResourceListEnvelopeViewModel?.displayedItemCount?.collectAsState()
        val lastIndex = recommendationsList?.value?.size?.minus(1)

        if (recommendationsList!!.value.isNotEmpty()) {
            recommendationUIModel.value = VendorRecommendationItemUIModel(recommendationsList.value)
        }

        if (!loadMoreState!!.value) {
            recommendationUIModel.value =
                recommendationUIModel.value.copy(recommendationList = recommendationsResourceListEnvelopeViewModel!!.resources.value)
        }


        LaunchedEffect(key1 = true) {
            recommendationsResourceListEnvelopeViewModel!!.clearData(mutableListOf())
            homepagePresenter.getRecommendations(vendorId)
        }


        AppTheme {
            Scaffold(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .background(color = Colors.dashboardBackground),
                snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
                topBar = {
                    RecommendationsScreenTitle(onBackPressed = {
                        mainViewModel!!.setOnBackPressed(true)
                    })
                },
                content = {
                    val selectedProduct = remember { mutableStateOf(Product()) }
                    var showProductDetailBottomSheet by remember { mutableStateOf(false) }

                    if (showProductDetailBottomSheet) {
                        mainViewModel!!.showProductBottomSheet(true)
                    } else {
                        mainViewModel!!.showProductBottomSheet(false)
                    }

                    if (selectedProduct.value.productId != -1L) {
                        ProductDetailBottomSheet(
                            mainViewModel!!,
                            isViewOnly = false,
                            OrderItem(itemProduct = selectedProduct.value),
                            onDismiss = {
                                selectedProduct.value = Product()
                            },
                            onAddToCart = { isAddToCart, item ->
                                showProductDetailBottomSheet = false
                            })
                    }
                    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                        val uiState = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()
                        if (uiState.value.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                    .padding(top = 40.dp, start = 50.dp, end = 50.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                IndeterminateCircularProgressBar()
                            }
                        } else if (uiState.value.isFailed) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                ErrorOccurredWidget(uiState.value.errorMessage, onRetryClicked = {
                                    homepagePresenter.getRecommendations(vendorId)
                                })
                            }
                        } else if (uiState.value.isEmpty) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptyContentWidget(emptyText = uiState.value.emptyMessage)
                            }
                        } else if (uiState.value.isSuccess) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .fillMaxHeight(),
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
                                            .background(color = Colors.dashboardBackground),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        LazyColumn(
                                            modifier = Modifier.fillMaxWidth()
                                                .height(
                                                    getVendorRecommendationListItemViewHeight(
                                                        recommendationUIModel.value.recommendationList
                                                    ).dp
                                                ),
                                            contentPadding = PaddingValues(
                                                top = 6.dp,
                                                bottom = 6.dp
                                            ),
                                            verticalArrangement = Arrangement.spacedBy(5.dp),
                                            userScrollEnabled = true
                                        ) {
                                            runBlocking {
                                                items(recommendationsList.value.size) { it ->
                                                    VendorRecommendationsItem(
                                                        recommendationUIModel.value.recommendationList[it],
                                                        mainViewModel!!,
                                                        onItemClickListener = {
                                                            when (it.recommendationType) {
                                                                RecommendationType.Services.toPath() -> {
                                                                    val bookingScreen =
                                                                        BookingScreen(
                                                                            platformNavigator!!
                                                                        )
                                                                    bookingScreen.setDatabaseBuilder(
                                                                        databaseBuilder!!
                                                                    )
                                                                    bookingScreen.setMainViewModel(
                                                                        mainViewModel!!
                                                                    )
                                                                    navigator.push(bookingScreen)
                                                                    mainViewModel!!.setSelectedService(
                                                                        it.serviceTypeItem?.serviceDetails!!
                                                                    )
                                                                    mainViewModel!!.setRecommendationServiceType(
                                                                        it.serviceTypeItem
                                                                    )
                                                                }

                                                                RecommendationType.Products.toPath() -> {
                                                                    selectedProduct.value =
                                                                        it.product!!
                                                                    showProductDetailBottomSheet =
                                                                        true
                                                                }
                                                            }
                                                        })

                                                    if (it == lastIndex && loadMoreState.value) {
                                                        Box(
                                                            modifier = Modifier.fillMaxWidth()
                                                                .height(60.dp),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            IndeterminateCircularProgressBar()
                                                        }
                                                    } else if (it == lastIndex && (displayedItemCount?.value!! < totalItemCount?.value!!)) {
                                                        val buttonStyle = Modifier
                                                            .height(60.dp)
                                                            .fillMaxWidth()
                                                            .padding(
                                                                top = 10.dp,
                                                                start = 10.dp,
                                                                end = 10.dp
                                                            )

                                                        ButtonComponent(
                                                            modifier = buttonStyle,
                                                            buttonText = "Show More",
                                                            borderStroke = BorderStroke(
                                                                1.dp,
                                                                Colors.primaryColor
                                                            ),
                                                            colors = ButtonDefaults.buttonColors(
                                                                backgroundColor = Color.White
                                                            ),
                                                            fontSize = 16,
                                                            shape = CircleShape,
                                                            textColor = Colors.primaryColor,
                                                            style = TextStyle()
                                                        ) {
                                                            if (recommendationsResourceListEnvelopeViewModel!!.nextPageUrl.value.isNotEmpty()) {
                                                                homepagePresenter.getMoreRecommendations(
                                                                    vendorId,
                                                                    nextPage = recommendationsResourceListEnvelopeViewModel!!.currentPage.value + 1
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
                },
                backgroundColor = Colors.dashboardBackground,
            )


        }
    }

    @Composable
    fun RecommendationsScreenTitle(onBackPressed: () -> Unit){
        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(40.dp)

        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                PageBackNavWidget {
                    onBackPressed()
                }
            }

            Box(modifier =  Modifier.weight(4.0f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                SubtitleTextWidget(text = "Recommendations", textColor = theme.styles.Colors.primaryColor)

            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
            }
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