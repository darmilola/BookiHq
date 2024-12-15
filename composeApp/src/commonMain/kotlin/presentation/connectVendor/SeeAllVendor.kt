package presentation.connectVendor

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
import domain.Models.VendorItemUIModel
import domain.Models.VendorRecommendationItemUIModel
import domain.Models.getVendorListItemViewHeight
import domain.Models.getVendorRecommendationListItemViewHeight
import drawable.ErrorOccurredWidget
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ConnectPageHandler
import presentation.DomainViewHandler.RecommendationsHandler
import presentation.appointmentBookings.BookingScreen
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.home.HomepagePresenter
import presentation.viewmodels.ConnectPageViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.RecommendationsResourceListEnvelopeViewModel
import presentation.viewmodels.VendorsResourceListEnvelopeViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.ProductDetailBottomSheet
import presentation.widgets.TitleWidget
import presentation.widgets.VendorRecommendationsItem
import rememberStackedSnackbarHostState
import theme.Colors
import utils.ParcelableScreen

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class SeeAllVendor(val platformNavigator: PlatformNavigator) : ParcelableScreen,
    KoinComponent,
    ScreenTransition {

    @Transient
    private val connectVendorPresenter: ConnectVendorPresenter by inject()
    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var switchVendorActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var connectPageViewModel: ConnectPageViewModel? = null
    @Transient
    private var vendorResourceListEnvelopeViewModel: VendorsResourceListEnvelopeViewModel? = null
    @Transient
    private val preferenceSettings: Settings = Settings()
    @Transient
    private var mainViewModel: MainViewModel? = null
    private var fromConnect: Boolean = false
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    fun isFromConnect(value: Boolean){
        fromConnect = value
    }

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        val vendorId: Long = preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath(),-1L]
        val country = preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath(), ""]
        val city = preferenceSettings[SharedPreferenceEnum.CITY.toPath(), ""]
        val navigator = LocalNavigator.currentOrThrow

        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        if (vendorResourceListEnvelopeViewModel == null) {
            vendorResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    VendorsResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }


        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (connectPageViewModel == null) {
            connectPageViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ConnectPageViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (switchVendorActionUIStateViewModel == null) {
            switchVendorActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        // View Contract Handler Initialisation
        val handler = ConnectPageHandler(
            vendorResourceListEnvelopeViewModel!!,
            loadingScreenUiStateViewModel!!,
            switchVendorActionUIStateViewModel!!,
            connectVendorPresenter)
        handler.init()

        val loadVendorUiState = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()

        val vendorList = vendorResourceListEnvelopeViewModel?.resources?.collectAsState()
        val selectedVendor = connectPageViewModel?.selectedVendor?.collectAsState()
        val loadMoreState = vendorResourceListEnvelopeViewModel!!.isLoadingMore.collectAsState()
        val totalVendorsCount =
            vendorResourceListEnvelopeViewModel!!.totalItemCount.collectAsState()
        val displayedVendorsCount =
            vendorResourceListEnvelopeViewModel!!.displayedItemCount.collectAsState()
        val lastIndex = vendorList!!.value.size.minus(1)
        val vendorUIModel = remember { mutableStateOf(VendorItemUIModel()) }


        LaunchedEffect(key1 = true) {
            if (vendorResourceListEnvelopeViewModel!!.resources.value.isEmpty()) {
                connectVendorPresenter.getVendor(
                    country = country,
                    city = city,
                    connectedVendor = vendorId
                )
            }
        }

        if (vendorList.value.isNotEmpty()){
            vendorUIModel.value = VendorItemUIModel(selectedVendor?.value!!, vendorList.value)
        }

        if (!loadMoreState.value) {
            vendorUIModel.value = vendorUIModel.value.copy(selectedVendor = selectedVendor!!.value,
                vendorsList = vendorResourceListEnvelopeViewModel!!.resources.value.map { it2 ->
                    it2.copy(
                        isSelected = it2.vendorId == selectedVendor.value.vendorId
                    )
                })
        }

        Scaffold(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Color.White),
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                SeeAllVendorScreenTitle(onBackPressed = {
                    mainViewModel!!.setOnBackPressed(true)
                })
            },
            content = {
                if (loadVendorUiState.value.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                }
                else if (loadVendorUiState.value.isFailed) {
                    Box(modifier = Modifier .fillMaxWidth().height(400.dp), contentAlignment = Alignment.Center) {
                        ErrorOccurredWidget(loadVendorUiState.value.errorMessage, onRetryClicked = {
                            connectVendorPresenter.getVendor(country = country, city = city, connectedVendor = vendorId)
                            vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                        })
                    }
                }
                else if (loadVendorUiState.value.isEmpty) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyContentWidget(emptyText = loadVendorUiState.value.emptyMessage)
                    }
                }

                else if (loadVendorUiState.value.isSuccess){

                        LazyColumn(
                            modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
                                .height(getVendorListItemViewHeight(vendorUIModel.value.vendorsList).dp),
                            contentPadding = PaddingValues(6.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp), userScrollEnabled = true
                        ) {
                            items(vendorUIModel.value.vendorsList.size) { i ->
                                SwitchVendorBusinessItemComponent(vendor = vendorUIModel.value.vendorsList[i]) {
                                   if (fromConnect) {
                                       mainViewModel!!.setSwitchVendorId(it.vendorId!!)
                                       mainViewModel!!.setSwitchVendor(it)
                                       val details = ConnectVendorDetailsScreen(it,platformNavigator)
                                       details.setMainViewModel(mainViewModel!!)
                                       details.setDatabaseBuilder(databaseBuilder)
                                       navigator.push(details)
                                   }
                                    else{
                                       mainViewModel!!.setSwitchVendorId(it.vendorId!!)
                                       mainViewModel!!.setSwitchVendor(it)
                                       val details = SwitchVendorDetails(platformNavigator)
                                       details.setMainViewModel(mainViewModel!!)
                                       details.setDatabaseBuilder(databaseBuilder)
                                       navigator.push(details)
                                    }
                                }
                                if (i == lastIndex && loadMoreState.value) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(60.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IndeterminateCircularProgressBar()
                                    }
                                }
                                else if (i == lastIndex && (displayedVendorsCount.value < totalVendorsCount.value)) {
                                    val buttonStyle = Modifier
                                        .height(55.dp)
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)

                                    ButtonComponent(
                                        modifier = buttonStyle,
                                        buttonText = "Show More",
                                        borderStroke = BorderStroke(
                                            1.dp,
                                            Colors.primaryColor
                                        ),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                        fontSize = 16,
                                        shape = CircleShape,
                                        textColor = Colors.primaryColor,
                                        style = TextStyle()
                                    ) {
                                        if (vendorResourceListEnvelopeViewModel!!.nextPageUrl.value.isNotEmpty()) {
                                            connectVendorPresenter.getMoreVendor(
                                                country = country,
                                                city = city,
                                                connectedVendor = vendorId,
                                                nextPage = vendorResourceListEnvelopeViewModel!!.currentPage.value + 1
                                            )
                                        }


                                    }
                                }
                            }
                        }
                }
            },
            backgroundColor = Color.White,
        )


    }

    @Composable
    fun SeeAllVendorScreenTitle(onBackPressed: () -> Unit){
        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
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

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                TitleWidget(title = "Nearby Parlors", textColor = theme.styles.Colors.primaryColor)

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