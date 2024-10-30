package presentation.home

import GGSansRegular
import StackedSnackbarHost
import UIStates.AppUIStates
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.device.ScreenSizeInfo
import applications.room.AppDatabase
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.Enums.AppointmentType
import domain.Models.HomepageInfo
import domain.Models.VendorRecommendation
import domain.Enums.RecommendationType
import domain.Enums.Screens
import domain.Enums.SharedPreferenceEnum
import domain.Models.OrderItem
import domain.Models.PlatformNavigator
import domain.Models.Product
import domain.Models.ServiceTypeItem
import domain.Models.Services
import domain.Models.UserAppointment
import domain.Models.Vendor
import domain.Models.VendorStatusModel
import drawable.ErrorOccurredWidget
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.HomepageHandler
import presentation.components.StraightLine
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.ShopStatusWidget
import presentation.widgets.HomeServicesWidget
import presentation.widgets.RecommendedServiceItem
import presentation.widgets.RecentAppointmentWidget
import presentation.widgets.PendingPackageAppointmentWidget
import presentation.widgets.ProductDetailBottomSheet
import presentation.widgets.RecentPackageAppointmentWidget
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.calculateHomePageScreenHeight
import utils.getRecentAppointmentViewHeight
import utils.getServicesViewHeight

@Parcelize
class HomeTab(val platformNavigator: PlatformNavigator) : Tab, KoinComponent, Parcelable {

    @Transient private val homepagePresenter: HomepagePresenter by inject()
    @Transient private val preferenceSettings: Settings = Settings()
    @Transient private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient private var mainViewModel: MainViewModel? = null
    @Transient private var homePageViewModel: HomePageViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

  @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = painterResource("drawable/home_icon.png")

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

    fun setHomePageViewModel(homePageViewModel: HomePageViewModel){
        this.homePageViewModel = homePageViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }



    @Composable
    override fun Content() {
            val userId = preferenceSettings[SharedPreferenceEnum.USER_ID.toPath(), -1L]
            val vendorPhone: String = preferenceSettings[SharedPreferenceEnum.VENDOR_WHATSAPP_PHONE.toPath(),""]
            val screenSizeInfo = ScreenSizeInfo()


            if (loadingScreenUiStateViewModel == null) {
                loadingScreenUiStateViewModel = kmpViewModel(
                    factory = viewModelFactory {
                        LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                    },
                )
            }


        LaunchedEffect(true) {
            if (homePageViewModel!!.homePageInfo.value.userInfo?.userId == null) {
                if (vendorPhone.isNotEmpty()){
                    homepagePresenter.getUserHomepageWithStatus(userId, vendorPhone)
                }
                else {
                    homepagePresenter.getUserHomepage(userId)
                }
            }
        }

        val handler = HomepageHandler(loadingScreenUiStateViewModel!!, homepagePresenter,
            onHomeInfoAvailable = { homePageInfo, vendorStatus ->
                val viewHeight = calculateHomePageScreenHeight(
                    homepageInfo = homePageInfo,
                    screenSizeInfo = screenSizeInfo,
                    statusList = vendorStatus
                )
                mainViewModel!!.setConnectedVendor(homePageInfo.vendorInfo!!)
                homePageViewModel!!.setHomePageViewHeight(viewHeight)
                homePageViewModel!!.setHomePageInfo(homePageInfo)
                homePageViewModel!!.setVendorStatus(vendorStatus)
                mainViewModel!!.setUserEmail(homePageInfo.userInfo?.email!!)
                mainViewModel!!.setUserFirstname(homePageInfo.userInfo.firstname!!)
                mainViewModel!!.setUserId(homePageInfo.userInfo.userId!!)
                mainViewModel!!.setVendorEmail(homePageInfo.vendorInfo.businessEmail!!)
                mainViewModel!!.setVendorId(homePageInfo.vendorInfo.vendorId!!)
                mainViewModel!!.setVendorBusinessLogoUrl(homePageInfo.vendorInfo.businessLogo!!)
                mainViewModel!!.setUserInfo(homePageInfo.userInfo)
                saveAccountInfoFromServer(homePageInfo)
            })
        handler.init()

        val homepageInfo = homePageViewModel!!.homePageInfo.collectAsState()
        val homePageViewHeight = homePageViewModel!!.homePageViewHeight.collectAsState()
        val uiState = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()

        if (homepageInfo.value.userInfo!!.userId != null){
            loadingScreenUiStateViewModel!!.switchScreenUIState(AppUIStates(isSuccess = true))
        }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )


            Scaffold(
                    snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
                    topBar = {},
                    content = {
                        Box(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                .background(color = Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            if (uiState.value.isLoading) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                        .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                                        .background(
                                            color = Color.Transparent,
                                            shape = RoundedCornerShape(20.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IndeterminateCircularProgressBar()
                                }
                            } else if (uiState.value.isFailed) {
                                ErrorOccurredWidget(uiState.value.errorMessage, onRetryClicked = {
                                    if (homePageViewModel!!.homePageInfo.value.userInfo?.userId == null) {
                                        if (vendorPhone.isNotEmpty()) {
                                            homepagePresenter.getUserHomepageWithStatus(
                                                userId,
                                                vendorPhone
                                            )
                                        } else {
                                            homepagePresenter.getUserHomepage(userId)
                                        }
                                    }
                                })
                            } else if (uiState.value.isSuccess) {
                                val recentAppointments = homepageInfo.value.recentAppointments
                                val vendorServices = homepageInfo.value.vendorServices
                                val vendorRecommendations =
                                    homepageInfo.value.recommendationRecommendations
                                Column(
                                    Modifier
                                        .verticalScroll(state = rememberScrollState())
                                        .height(homePageViewHeight.value.dp)
                                        .fillMaxWidth()
                                        .padding(top = 5.dp, bottom = 100.dp)

                                ) {
                                    if (!vendorServices.isNullOrEmpty()) {
                                        AttachOurServices()
                                        ServiceGridScreen(vendorServices, onServiceSelected = {
                                            mainViewModel!!.setRecommendationServiceType(ServiceTypeItem())
                                            mainViewModel!!.setScreenNav(
                                                Pair(
                                                    Screens.MAIN_SCREEN.toPath(),
                                                    Screens.BOOKING.toPath()
                                                )
                                            )
                                            mainViewModel!!.setSelectedService(it)
                                        })
                                    }
                                    if (homePageViewModel!!.vendorStatus.value.isNotEmpty()) {
                                        BusinessStatusDisplay(
                                            statusList = homePageViewModel!!.vendorStatus.value,
                                            vendorInfo = mainViewModel!!.connectedVendor.value
                                        )
                                    }
                                    if (!vendorRecommendations.isNullOrEmpty()) {
                                        RecommendedSessions(vendorRecommendations, mainViewModel!!)
                                    }
                                    if (!recentAppointments.isNullOrEmpty()) {
                                        AttachAppointmentsTitle("Recent Appointments")
                                        AppointmentsScreen(
                                            appointmentList = recentAppointments,
                                            platformNavigator = platformNavigator
                                        )
                                    }
                                }
                            }
                        }
                    })

    }

    private fun saveAccountInfoFromServer(homePageInfo: HomepageInfo){
        val preferenceSettings = Settings()
        preferenceSettings[SharedPreferenceEnum.USER_EMAIL.toPath()] = homePageInfo.userInfo?.email
        preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = homePageInfo.userInfo?.userId
        preferenceSettings[SharedPreferenceEnum.FIRSTNAME.toPath()] = homePageInfo.userInfo?.firstname
        preferenceSettings[SharedPreferenceEnum.VENDOR_EMAIL.toPath()] = homePageInfo.vendorInfo?.businessEmail
        preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath()] = homePageInfo.vendorInfo?.vendorId
        preferenceSettings[SharedPreferenceEnum.VENDOR_BUSINESS_LOGO.toPath()] = homePageInfo.vendorInfo?.businessLogo
    }

    @Composable
    fun ServiceGridScreen(vendorServices: List<Services>, onServiceSelected: (Services) -> Unit) {
        val viewHeight = getServicesViewHeight(vendorServices)
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().height(viewHeight.dp),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            userScrollEnabled = false
        ) {
            items(vendorServices.size) {
                HomeServicesWidget(vendorServices[it], onServiceSelected = {
                    onServiceSelected(it)
                })
            }
        }
     }

@Composable
    fun AttachOurServices(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 20.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Services",
                    fontSize = 16,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = Modifier.fillMaxWidth(0.20f)
                )
                StraightLine()
            }
    }

    @Composable
    fun RecommendedSessions(recommendations: List<VendorRecommendation>, mainViewModel: MainViewModel) {
        Column(modifier = Modifier.fillMaxWidth().height(450.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .height(40.dp)
                .fillMaxWidth()
        ) {
            TextComponent(
                text = "Recommendation",
                textModifier = Modifier.fillMaxWidth(0.35f),
                fontSize = 16,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            StraightLine()
        }

        RecommendedAppointmentList(recommendations, mainViewModel)
       }

    }


    @Composable
    fun AttachAppointmentsTitle(title: String){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 20.dp, bottom = 20.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = title,
                    fontSize = 16,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 10,
                    textModifier = Modifier.fillMaxWidth(0.20f)
                )
                StraightLine()
            }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun RecommendedAppointmentList(recommendations: List<VendorRecommendation>, mainViewModel: MainViewModel) {
        val selectedProduct = remember { mutableStateOf(Product()) }
        var showProductDetailBottomSheet by remember { mutableStateOf(false) }

        if (showProductDetailBottomSheet) {
            mainViewModel.showProductBottomSheet(true)
        }
        else{
            mainViewModel.showProductBottomSheet(false)
        }

        if (selectedProduct.value.productId != -1L) {
            ProductDetailBottomSheet(
                mainViewModel,
                isViewOnly = false,
                OrderItem(itemProduct = selectedProduct.value),
                onDismiss = {
                    selectedProduct.value = Product()
                },
                onAddToCart = { isAddToCart, item ->
                    showProductDetailBottomSheet = false

                })
        }



        val pagerState = rememberPagerState(pageCount = {
            recommendations.size
        })


        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.95f),
                    pageSpacing = 10.dp
                ) { page ->
                    RecommendedServiceItem(recommendations[page],mainViewModel, onItemClickListener = {
                        when (it.recommendationType) {
                            RecommendationType.Services.toPath() -> {
                                mainViewModel.setScreenNav(
                                    Pair(
                                        Screens.MAIN_SCREEN.toPath(),
                                        Screens.BOOKING.toPath()
                                    )
                                )
                                mainViewModel.setSelectedService(it.serviceTypeItem?.serviceDetails!!)
                                mainViewModel.setRecommendationServiceType(it.serviceTypeItem)
                            }

                            RecommendationType.Products.toPath() -> {
                                selectedProduct.value = it.product!!
                                showProductDetailBottomSheet = true
                            }
                        }
                    })
                }
                Row(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(recommendations.size) { iteration ->
                        val color: Color
                        val width: Int
                        if (pagerState.currentPage == iteration) {
                            color = Colors.primaryColor
                            width = 20
                        } else {
                            color = Color.LightGray
                            width = 20
                        }
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .height(2.dp)
                                .width(width.dp)
                        )
                    }

                }

        }

    }

    @Composable
    fun AppointmentsScreen(appointmentList: List<UserAppointment>?, platformNavigator: PlatformNavigator) {
        if (appointmentList != null) {
            val viewHeight = getRecentAppointmentViewHeight(appointmentList)
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .height(viewHeight.dp), userScrollEnabled = false
            ) {
                items(key = { it -> it.appointmentId }, items =  appointmentList) { item ->
                    if (item.resources!!.appointmentType == AppointmentType.SINGLE.toPath()) {
                        RecentAppointmentWidget(userAppointment = item)
                      }
                       else if (item.resources.appointmentType == AppointmentType.PACKAGE.toPath()) {
                            RecentPackageAppointmentWidget(item)
                        }

                }

            }
        }
    }


    @Composable
    fun BusinessStatusDisplay(statusList: List<VendorStatusModel>, vendorInfo: Vendor) {
        val modifier =
            Modifier.fillMaxWidth()
                .height(700.dp)
                .background(color = Color.Black)
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            ShopStatusWidget(statusList, vendorInfo)
        }
    }
}