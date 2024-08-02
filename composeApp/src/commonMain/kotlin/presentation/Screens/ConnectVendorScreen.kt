package presentation.Screens

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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import domain.Models.VendorItemUIModel
import domain.Models.getVendorListItemViewHeight
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.widgets.SearchBar
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.ConnectPageViewModel
import presentation.viewmodels.UIStateViewModel
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.Transient
import presentation.DomainViewHandler.ConnectPageHandler
import presentation.connectVendor.ConnectVendorPresenter
import presentation.connectVendor.SwitchVendorBusinessItemComponent
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.VendorsResourceListEnvelopeViewModel
import presentation.widgets.ConnectVendorHeader
import theme.Colors
import utils.ParcelableScreen


@Parcelize
class ConnectVendorScreen(val platformNavigator: PlatformNavigator? = null) : ParcelableScreen, KoinComponent {

    @Transient private val preferenceSettings: Settings = Settings()
    @Transient private val connectVendorPresenter: ConnectVendorPresenter by inject()
    @Transient private var uiStateViewModel: UIStateViewModel? = null
    @Transient private var connectPageViewModel: ConnectPageViewModel? = null
    @Transient private var mainViewModel: MainViewModel? = null
    @Transient private var vendorResourceListEnvelopeViewModel: VendorsResourceListEnvelopeViewModel? = null
    private var country: String = ""

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val contentVisible = remember { mutableStateOf(false) }
        val contentLoading = remember { mutableStateOf(false) }
        val errorVisible = remember { mutableStateOf(false) }
        val searchQuery = remember { mutableStateOf("") }
        country = preferenceSettings["country", ""]

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setExitApp(true)
        }

        if (vendorResourceListEnvelopeViewModel == null) {
            vendorResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    VendorsResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }


        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
            vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf())
            connectVendorPresenter.getVendor(country = country)
        }

        if (connectPageViewModel == null) {
            connectPageViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ConnectPageViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val loadMoreState = vendorResourceListEnvelopeViewModel!!.isLoadingMore.collectAsState()
        val vendorList = vendorResourceListEnvelopeViewModel?.resources?.collectAsState()
        val selectedVendor = connectPageViewModel?.selectedVendor?.collectAsState()
        val totalVendorsCount = vendorResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
        val displayedVendorsCount = vendorResourceListEnvelopeViewModel?.displayedItemCount?.collectAsState()
        var vendorUIModel by remember { mutableStateOf(VendorItemUIModel(selectedVendor?.value!!, vendorList!!.value)) }
        val lastIndex = vendorList!!.value.size.minus(1)


        if(!loadMoreState.value) {
            vendorUIModel = vendorUIModel.copy(selectedVendor = selectedVendor?.value!!,
                vendorsList = vendorResourceListEnvelopeViewModel?.resources?.value!!.map { it2 ->
                    it2.copy(
                        isSelected = it2.vendorId == selectedVendor.value.vendorId
                    )
                })
           }


        // View Contract Handler Initialisation
        val handler = ConnectPageHandler(
            vendorResourceListEnvelopeViewModel!!,
            uiStateViewModel!!,
            connectVendorPresenter,
            onPageLoading = {
                contentLoading.value = true
            },
            onContentVisible = {
                contentLoading.value = false
                contentVisible.value = true
            },
            onErrorVisible = {
                errorVisible.value = true
                contentLoading.value = false
            },
            onConnected = {})
        handler.init()

           Scaffold(
                topBar = {
                    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        ConnectVendorHeader(title = "Connect Vendor")
                        SearchBar(placeholderText = "search @vendor", onValueChange = {
                            vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf<Vendor>())
                            searchQuery.value = it
                            connectVendorPresenter.searchVendor(country, searchQuery = it)
                        }, onBackPressed = {
                            vendorResourceListEnvelopeViewModel!!.clearData(mutableListOf<Vendor>())
                            connectVendorPresenter.getVendor(country = country)
                        })
                    }
                },
                content = {
                    if (contentLoading.value) {
                        //Content Loading
                        Box(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                .padding(start = 50.dp, end = 50.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            IndeterminateCircularProgressBar()
                        }
                    } else if (errorVisible.value) {

                     // Error Occurred display reload

                    } else if (contentVisible.value) {
                        LazyColumn(
                            modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
                                .height(getVendorListItemViewHeight(vendorUIModel.vendorsList).dp),
                            contentPadding = PaddingValues(6.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp), userScrollEnabled = true
                        ) {
                            items(vendorUIModel.vendorsList.size) { i ->
                                SwitchVendorBusinessItemComponent(vendor = vendorUIModel.vendorsList[i]) {
                                    val connectVendorDetailsScreen = ConnectVendorDetailsScreen(vendor = it, platformNavigator = platformNavigator!!)
                                    connectVendorDetailsScreen.setMainViewModel(mainViewModel!!)
                                    navigator.push(connectVendorDetailsScreen)
                                }
                                if (i == lastIndex && loadMoreState.value) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(60.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IndeterminateCircularProgressBar()
                                    }
                                }
                                else if (i == lastIndex && (displayedVendorsCount!!.value < totalVendorsCount!!.value)) {
                                    val buttonStyle = Modifier
                                        .height(50.dp)
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
                                        if (!vendorResourceListEnvelopeViewModel?.nextPageUrl?.value.isNullOrEmpty()) {
                                            if (searchQuery.value.isNotEmpty()) {
                                                connectVendorPresenter.searchMoreVendors(
                                                    country,
                                                    searchQuery.value,
                                                    vendorResourceListEnvelopeViewModel?.currentPage?.value!! + 1
                                                )
                                            } else {
                                                connectVendorPresenter.getMoreVendor(
                                                    country,
                                                    vendorResourceListEnvelopeViewModel?.currentPage?.value!! + 1
                                                )
                                            }
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
    }
