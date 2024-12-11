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
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PackagesResourceListEnvelopeViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.PackageItem
import rememberStackedSnackbarHostState
import theme.Colors

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
            packagesResourceListEnvelopeViewModel!!,
            loadingScreenUiStateViewModel!!)
        handler.init()

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        val loadMoreState = packagesResourceListEnvelopeViewModel?.isLoadingMore?.collectAsState()
        val packageList = packagesResourceListEnvelopeViewModel?.resources?.collectAsState()
        val totalItemsCount = packagesResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
        val displayedItemsCount = packagesResourceListEnvelopeViewModel?.displayedItemCount?.collectAsState()
        val lastIndex = packageList?.value?.size?.minus(1)

        LaunchedEffect(true) {
            val isSwitchVendor: Boolean = mainViewModel!!.isSwitchVendor.value
            if (isSwitchVendor){
                packagesResourceListEnvelopeViewModel!!.clearData(mutableListOf())
            }
            if (packagesResourceListEnvelopeViewModel!!.resources.value.isNotEmpty()){
                loadingScreenUiStateViewModel!!.switchScreenUIState(AppUIStates(isSuccess = true))
            }
            else {
                packagePresenter.getVendorPackages(vendorId)
            }
        }

        val selectedPackage = remember { mutableStateOf(VendorPackage()) }

        var packageUiModel by remember {
            mutableStateOf(
                VendorPackageItemUIModel(
                    selectedPackage.value,
                    packagesResourceListEnvelopeViewModel!!.resources.value
                )
            )
        }

        if (!loadMoreState?.value!!) {
            packageUiModel =
                packageUiModel.copy(selectedPackage = selectedPackage.value,
                    packageList = packagesResourceListEnvelopeViewModel!!.resources.value)
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
                                        modifier = Modifier.fillMaxWidth().height((packageList!!.value.size * 250).dp),
                                        contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                                        verticalArrangement = Arrangement.spacedBy(5.dp),
                                        userScrollEnabled = true
                                    ) {
                                        runBlocking {
                                            items(packageUiModel.packageList.size) { it ->
                                                PackageItem(packageUiModel.packageList[it], onPackageClickListener = {
                                                    mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.PACKAGE_INFO.toPath()))
                                                    mainViewModel!!.setSelectedPackage(it)
                                                })

                                                if (it == lastIndex && loadMoreState.value) {
                                                    Box(
                                                        modifier = Modifier.fillMaxWidth().height(60.dp),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        IndeterminateCircularProgressBar()
                                                    }
                                                } else if (it == lastIndex && (displayedItemsCount?.value!! < totalItemsCount?.value!!)) {
                                                    val buttonStyle = Modifier
                                                        .height(60.dp)
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
                                                        if (packagesResourceListEnvelopeViewModel!!.nextPageUrl.value.isNotEmpty()) {
                                                            packagePresenter.getMoreVendorPackages(vendorId, packagesResourceListEnvelopeViewModel!!.currentPage.value + 1)

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
            backgroundColor = Color.Transparent,
        )
    }
}