package presentation.connectVendor

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
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
import com.russhwolf.settings.set
import domain.Enums.SharedPreferenceEnum
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import domain.Models.VendorItemUIModel
import domain.Models.getVendorListItemViewHeight
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ConnectPageHandler
import presentation.widgets.SearchBar
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.ConnectPageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.VendorsResourceListEnvelopeViewModel
import presentation.widgets.SwitchVendorHeader
import presentation.widgets.SwitchVendorBottomSheet
import theme.Colors
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
    private var actionPerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var connectPageViewModel: ConnectPageViewModel? = null
    @Transient
    private var vendorResourceListEnvelopeViewModel: VendorsResourceListEnvelopeViewModel? = null
    @Transient
    private val preferenceSettings: Settings = Settings()
    @Transient
    private var mainViewModel: MainViewModel? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        val searchQuery = remember { mutableStateOf("") }
        var country = preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath(), ""]
        var city = preferenceSettings[SharedPreferenceEnum.CITY.toPath(), ""]
        val navigator = LocalNavigator.currentOrThrow

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
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

        if (actionPerformedActionUIStateViewModel == null) {
            actionPerformedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        LaunchedEffect(key1 = true) {
            if (preferenceSettings[SharedPreferenceEnum.LATITUDE.toPath(), ""].isNotEmpty()
                && preferenceSettings[SharedPreferenceEnum.LONGITUDE.toPath(), ""].isNotEmpty()
                && preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath(), ""].isNotEmpty()
                && preferenceSettings[SharedPreferenceEnum.CITY.toPath(), ""].isNotEmpty()){
                connectVendorPresenter.getVendor(country = country, city = city)
                vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf())
            }
            else{
                platformNavigator.getUserLocation(onLocationReady = { latitude: String, longitude: String, countryName: String, cityName: String ->
                    preferenceSettings[SharedPreferenceEnum.LATITUDE.toPath()] = latitude
                    preferenceSettings[SharedPreferenceEnum.LONGITUDE.toPath()] = longitude
                    preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = countryName
                    preferenceSettings[SharedPreferenceEnum.CITY.toPath()] = cityName
                    connectVendorPresenter.getVendor(country = countryName, city = cityName)
                    vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                    city = cityName
                    country = countryName
                })
            }
        }


        val initializingScreen = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()
        val vendorList = vendorResourceListEnvelopeViewModel?.resources?.collectAsState()
        val selectedVendor = connectPageViewModel?.selectedVendor?.collectAsState()
        val vendorUIModel = remember { mutableStateOf(VendorItemUIModel()) }

        if (vendorList!!.value.isNotEmpty()){
            vendorUIModel.value = VendorItemUIModel(selectedVendor?.value!!, vendorList.value)
        }

        // View Contract Handler Initialisation
        val handler = ConnectPageHandler(
            vendorResourceListEnvelopeViewModel!!,
            loadingScreenUiStateViewModel!!,
            actionPerformedActionUIStateViewModel!!,
            connectVendorPresenter)
        handler.init()

        var showSwitchReasonBottomSheet by remember { mutableStateOf(false) }

        if (showSwitchReasonBottomSheet) {
            SwitchVendorBottomSheet(onDismiss = {
                showSwitchReasonBottomSheet = false
            }, onConfirmation = {
                mainViewModel!!.setSwitchVendorReason(it)
                showSwitchReasonBottomSheet = false
                val details = SwitchVendorDetails(platformNavigator)
                details.setMainViewModel(mainViewModel!!)
                navigator.push(details)
            })

        }


        Scaffold(
            topBar = {
                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    SwitchVendorHeader(onBackPressed = {
                        navigator.pop()
                    })
                    SearchBar(placeholderText = "search @vendor", onValueChange = {
                        vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf<Vendor>())
                        searchQuery.value = it
                        connectVendorPresenter.searchVendor(country,searchQuery = it)
                    }, onBackPressed = {
                        vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf<Vendor>())
                        connectVendorPresenter.getVendor(country = country, city = city)
                    })
                }
            },
            content = {
                if (initializingScreen.value.isLoading) {
                    //Content Loading
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (initializingScreen.value.isFailed) {

                    // Error Occurred display reload

                } else if (initializingScreen.value.isSuccess) {
                    LazyColumn(
                        modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
                            .height(getVendorListItemViewHeight(vendorUIModel.value.vendorsList).dp),
                        contentPadding = PaddingValues(6.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp), userScrollEnabled = true
                    ) {
                     runBlocking {
                        items(vendorUIModel.value.vendorsList.size) { i ->
                            SwitchVendorBusinessItemComponent(vendor = vendorUIModel.value.vendorsList[i]) {
                                mainViewModel!!.setSwitchVendorID(it.vendorId!!)
                                mainViewModel!!.setSwitchVendor(it)
                                showSwitchReasonBottomSheet = true
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