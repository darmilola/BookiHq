package presentation.account

import GGSansSemiBold
import StackedSnackbarHost
import UIStates.AppUIStates
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.Screens
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ProfileHandler
import presentation.components.RightIconButtonComponent
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.profile.ProfilePresenter
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Parcelize
class JoinASpa(private val platformNavigator: PlatformNavigator) : Tab,KoinComponent, Parcelable {

    @Transient private var mainViewModel: MainViewModel? = null
    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Join A Spa"
            val icon = painterResource("profile_icon.png")

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

        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel= kmpViewModel(
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

        val vendorInfo = remember { mutableStateOf(Vendor()) }
        val uiState = performedActionUIStateViewModel!!.uiStateInfo.collectAsState()

        val profileHandler = ProfileHandler(profilePresenter,
            onUserLocationReady = {},
            onVendorInfoReady = { it ->
               vendorInfo.value = it
            },
            performedActionUIStateViewModel!!)
        profileHandler.init()


        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )
        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                JoinASpaTopBar(mainViewModel!!)
            },
            backgroundColor = Color.White,
            floatingActionButton = {},
            content = {

                if (uiState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LoadingDialog("Getting Vendor")
                    }
                }
                else if (uiState.value.isSuccess) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        performedActionUIStateViewModel!!.switchActionUIState(AppUIStates(isDefault = true))
                        mainViewModel!!.setJoinSpaVendor(vendorInfo.value)
                        mainViewModel!!.setScreenNav(Pair(Screens.JOIN_SPA.toPath(), Screens.JOIN_SPA_INFO.toPath()))
                    }
                }
                else if (uiState.value.isFailed) {
                    ErrorDialog("Error Occurred", "Close", onConfirmation = {})
                }

                val columnModifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                Box(modifier = Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxHeight(0.90f)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = columnModifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            AppLogo("drawable/spa_icon.png")

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp)
                                    .wrapContentHeight(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {

                                TextComponent(
                                    text = "Join A Spa",
                                    fontSize = 35,
                                    fontFamily = GGSansSemiBold,
                                    textStyle = TextStyle(),
                                    textColor = Colors.darkPrimary,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Black,
                                    lineHeight = 45,
                                    letterSpacing = 1,
                                    textModifier = Modifier
                                        .fillMaxWidth())

                            }


                            TextComponent(
                                text = "Scan Shop QR To Continue",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = MaterialTheme.typography.h6,
                                textColor = Color.LightGray,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 20,
                                maxLines = 2,
                                textModifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                                overflow = TextOverflow.Ellipsis)

                            Column(modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally) {

                                 val buttonStyle = Modifier
                                     .padding(bottom = 10.dp, top = 10.dp, start = 20.dp, end = 20.dp)
                                     .fillMaxWidth()
                                     .height(50.dp)

                                 RightIconButtonComponent(modifier = buttonStyle, buttonText = "Scan QR", borderStroke = BorderStroke(0.8.dp, Colors.darkPrimary), colors = ButtonDefaults.buttonColors(backgroundColor = Colors.lightPrimaryColor), fontSize = 16, shape = CircleShape, textColor = Colors.darkPrimary, style = MaterialTheme.typography.h4, iconRes = "drawable/forward_arrow.png",  colorFilter = ColorFilter.tint(color = Colors.darkPrimary)){
                                      platformNavigator.startScanningBarCode {
                                          val vendorId =  it.toLongOrNull()
                                          if (vendorId != null) {
                                              profilePresenter.getVendorAccountInfo(it.toLong())
                                          }
                                      }
                                 }
                               }
                            }

                    }
                }


            }
        )
    }
    }

    @Composable
    fun AppLogo(logoUrl: String) {
        Box(modifier = Modifier
                .size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(
                imageModifier = Modifier.fillMaxSize().padding(3.dp), imageRes = logoUrl)
        }
    }


