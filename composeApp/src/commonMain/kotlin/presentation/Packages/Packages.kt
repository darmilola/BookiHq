package presentation.Packages

import StackedSnackbarHost
import UIStates.AppUIStates
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Enums.Screens
import domain.Enums.SharedPreferenceEnum
import domain.Models.VendorPackage
import domain.Models.VendorPackageItemUIModel
import drawable.ErrorOccurredWidget
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.PackagesHandler
import presentation.DomainViewHandler.ShopProductsHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PackagesResourceListEnvelopeViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.PackageItem
import presentation.widgets.ProductItem
import rememberStackedSnackbarHostState
import utils.getProductViewHeight

@Parcelize
class Packages : Tab, KoinComponent, Parcelable {

    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private val packagePresenter: PackagePresenter by inject()
    @Transient
    val preferenceSettings = Settings()
    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var packagesResourceListEnvelopeViewModel: PackagesResourceListEnvelopeViewModel? = null

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Packages"
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

    @Composable
    override fun Content() {
        val vendorId: Long = preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath(),-1L]

        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (packagesResourceListEnvelopeViewModel == null) {
            packagesResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PackagesResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }

        val handler = PackagesHandler(packagePresenter,
            loadingScreenUiStateViewModel!!, onVendorPackageReady = {
                packagesResourceListEnvelopeViewModel!!.setResources(it)
            })
        handler.init()

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        val isSwitchVendor: Boolean = preferenceSettings[SharedPreferenceEnum.IS_SWITCH_VENDOR.toPath(),false]
        if (isSwitchVendor){
            packagesResourceListEnvelopeViewModel!!.setResources(arrayListOf())
        }

        val packageList = packagesResourceListEnvelopeViewModel!!.resources.collectAsState()

        LaunchedEffect(true) {
            if (packageList.value.isNotEmpty()){
                loadingScreenUiStateViewModel!!.switchScreenUIState(AppUIStates(isSuccess = true))
            }
            else {
                packagePresenter.getVendorPackages(vendorId)
            }

        }

        Scaffold(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Color.White),
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {
                Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
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
                                packagePresenter.getVendorPackages(vendorId)
                            })
                        }
                    }
                    else if (uiState.value.isEmpty) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            EmptyContentWidget(emptyText = uiState.value.emptyMessage)
                        }
                    }
                    else if (uiState.value.isSuccess) {
                        PackageContent(vendorPackages = packageList.value, onPackageSelected = {
                            mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.PACKAGE_INFO.toPath()))
                            mainViewModel!!.setSelectedPackage(it)
                        })
                    }

                }
            },
            backgroundColor = Color.Transparent,
        )
    }


    @Composable
    fun PackageContent(
        vendorPackages: List<VendorPackage>,
        onPackageSelected: (VendorPackage) -> Unit) {
        val selectedPackage = remember { mutableStateOf(VendorPackage()) }

        val packageUiModel by remember {
            mutableStateOf(
                VendorPackageItemUIModel(
                    selectedPackage.value,
                    vendorPackages
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
                        modifier = Modifier.fillMaxWidth().height((vendorPackages.size * 250).dp),
                        contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        userScrollEnabled = true
                    ) {
                        runBlocking {
                            items(packageUiModel.packageList.size) { it ->
                                PackageItem(packageUiModel.packageList[it], onPackageClickListener = {
                                     onPackageSelected(it)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}