package presentation.connectVendor

import UIStates.AppUIStates
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import domain.Enums.SharedPreferenceEnum
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import domain.Models.VendorItemUIModel
import domain.Models.getVendorListItemViewHeight
import drawable.ErrorOccurredWidget
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ConnectPageHandler
import presentation.DomainViewHandler.ProfileHandler
import presentation.widgets.SearchBar
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.profile.ProfilePresenter
import presentation.viewmodels.ConnectPageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.VendorsResourceListEnvelopeViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.SwitchVendorHeader
import presentations.components.ImageComponent
import theme.styles.Colors
import utils.ParcelableScreen

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class SwitchVendor(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent,
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
    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null
    @Transient
    private var barCodePerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    @Composable
    override fun Content() {
        val searchQuery = remember { mutableStateOf("") }
        val country = preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath(), ""]
        val city = preferenceSettings[SharedPreferenceEnum.CITY.toPath(), ""]
        val vendorId: Long = preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath(),-1L]
        val navigator = LocalNavigator.currentOrThrow

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }
        platformNavigator.requestCameraPermission()

        if (vendorResourceListEnvelopeViewModel == null) {
            vendorResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    VendorsResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }

        if (barCodePerformedActionUIStateViewModel == null) {
            barCodePerformedActionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
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

        LaunchedEffect(key1 = true) {
            vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf())
            if (vendorResourceListEnvelopeViewModel!!.newVendors.value.isEmpty()) {
                connectVendorPresenter.viewVendors(
                    country = country,
                    city = city,
                    connectedVendor = vendorId
                )
            }
        }

        val vendorInfo = remember { mutableStateOf(Vendor()) }
        val barCodeUiState = barCodePerformedActionUIStateViewModel!!.uiStateInfo.collectAsState()

        val profileHandler = ProfileHandler(profilePresenter,
            onUserLocationReady = {},
            onVendorInfoReady = { it ->
                vendorInfo.value = it
            },
            barCodePerformedActionUIStateViewModel!!)
        profileHandler.init()

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
        val nearbyVendor = vendorResourceListEnvelopeViewModel!!.nearbyVendors.collectAsState()
        val newVendor = vendorResourceListEnvelopeViewModel!!.newVendors.collectAsState()

        val loadMoreState = vendorResourceListEnvelopeViewModel!!.isLoadingMore.collectAsState()
        val totalVendorsCount =
            vendorResourceListEnvelopeViewModel!!.totalItemCount.collectAsState()
        val displayedVendorsCount =
            vendorResourceListEnvelopeViewModel!!.displayedItemCount.collectAsState()
        val lastIndex = vendorList!!.value.size.minus(1)
        val vendorUIModel = remember { mutableStateOf(VendorItemUIModel()) }

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
            floatingActionButton = {
                Box(
                    modifier = Modifier.size(140.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    Box(
                        Modifier
                            .clip(CircleShape)
                            .size(70.dp)
                            .clickable {
                                platformNavigator.startScanningBarCode {
                                    val vendorId =  it.toLongOrNull()
                                    if (vendorId != null) {
                                        profilePresenter.getVendorAccountInfo(it.toLong())
                                    }
                                }
                            }
                            .background(color = Colors.darkPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        val modifier = Modifier
                            .size(40.dp)
                        ImageComponent(
                            imageModifier = modifier,
                            imageRes = "drawable/shopping_cart.png",
                            colorFilter = ColorFilter.tint(color = Color.White)
                        )
                    }
                }
            },
            topBar = {
                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    SwitchVendorHeader(onBackPressed = {
                        navigator.pop()
                    })
                    SearchBar(placeholderText = "search @parlor", searchIcon = "drawable/search_icon.png" ,onValueChange = {
                        vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf<Vendor>())
                        searchQuery.value = it
                        connectVendorPresenter.searchVendor(country,connectedVendor = vendorId,searchQuery = it)
                    }, onBackPressed = {
                        searchQuery.value = ""
                        vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf<Vendor>())
                        connectVendorPresenter.viewVendors(
                            country = country,
                            city = city,
                            connectedVendor = vendorId
                        )
                    })
                }
            },
            content = {
                if (barCodeUiState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LoadingDialog("Getting Parlor")
                    }
                }
                else if (barCodeUiState.value.isSuccess) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        barCodePerformedActionUIStateViewModel!!.switchActionUIState(AppUIStates(isDefault = true))
                        mainViewModel!!.setSwitchVendorId(vendorInfo.value.vendorId!!)
                        mainViewModel!!.setSwitchVendor(vendorInfo.value)
                        val details = SwitchVendorDetails(platformNavigator)
                        details.setMainViewModel(mainViewModel!!)
                        details.setDatabaseBuilder(databaseBuilder)
                        navigator.push(details)
                    }
                }
                else if (barCodeUiState.value.isFailed) {
                    ErrorDialog("Error Occurred", "Close", onConfirmation = {})
                }
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
                            vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                            connectVendorPresenter.viewVendors(
                                country = country,
                                city = city,
                                connectedVendor = vendorId
                            )
                            })
                    }
                }
                else if (loadVendorUiState.value.isEmpty) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyContentWidget(emptyText = loadVendorUiState.value.emptyMessage)
                    }
                }
                else if (loadVendorUiState.value.isSuccess) {
                    if (searchQuery.value.isEmpty()) {
                        VendorsView(
                            nearbyVendor = nearbyVendor.value,
                            newVendor = newVendor.value,
                            mainViewModel = mainViewModel!!,
                            onSeeAllNearbyVendor = {
                                val seeAllVendor = SeeAllVendor(platformNavigator)
                                seeAllVendor.isFromConnect(false)
                                seeAllVendor.setMainViewModel(mainViewModel!!)
                                seeAllVendor.setDatabaseBuilder(databaseBuilder)
                                navigator.push(seeAllVendor)
                            },
                            onVendorClickListener = {
                                mainViewModel!!.setSwitchVendorId(it.vendorId!!)
                                mainViewModel!!.setSwitchVendor(it)
                                val details = SwitchVendorDetails(platformNavigator)
                                details.setMainViewModel(mainViewModel!!)
                                details.setDatabaseBuilder(databaseBuilder)
                                navigator.push(details)
                            })
                    }
                    else {
                         LazyColumn(
                        modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
                            .height(getVendorListItemViewHeight(vendorUIModel.value.vendorsList).dp),
                        contentPadding = PaddingValues(6.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp), userScrollEnabled = true
                    ) {
                         items(vendorUIModel.value.vendorsList.size) { i ->
                             SwitchVendorBusinessItemComponent(vendor = vendorUIModel.value.vendorsList[i]) {
                                 mainViewModel!!.setSwitchVendorId(it.vendorId!!)
                                 mainViewModel!!.setSwitchVendor(it)
                                 val details = SwitchVendorDetails(platformNavigator)
                                 details.setMainViewModel(mainViewModel!!)
                                 details.setDatabaseBuilder(databaseBuilder)
                                 navigator.push(details)
                             }
                         }
                    }
                    }
                }
            },
            backgroundColor = Color.White,
        )

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