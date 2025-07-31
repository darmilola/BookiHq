package presentation.home

import GGSansBold
import GGSansRegular
import GGSansSemiBold
import StackedSnackbarHost
import UIStates.AppUIStates
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.TextStyle
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
import presentation.widgets.ErrorOccurredWidget
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.HomepageHandler
import presentation.components.StraightLine
import presentation.components.IndeterminateCircularProgressBar
import presentation.main.VendorLogo
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.ActionItemComponent
import presentation.widgets.HomeServicesWidget
import presentation.widgets.VendorRecommendationsItem
import presentation.widgets.RecentAppointmentWidget
import presentation.widgets.ProductDetailBottomSheet
import presentation.widgets.RecentPackageAppointmentWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.calculateHomePageScreenHeight
import utils.getHourOfDayDisplay
import utils.getRecentAppointmentViewHeight

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



    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val userId = preferenceSettings[SharedPreferenceEnum.USER_ID.toPath(), -1L]
        preferenceSettings[SharedPreferenceEnum.AUTH_ONBOARDING.toPath()] = true
        val screenSizeInfo = ScreenSizeInfo()

        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }



        LaunchedEffect(true) {
            val isSwitchVendor: Boolean = mainViewModel!!.isSwitchVendor.value
            if (isSwitchVendor) {
                homePageViewModel!!.setHomePageInfo(HomepageInfo())
            }

            if (homePageViewModel!!.homePageInfo.value.userInfo?.userId == null) {
                homepagePresenter.getUserHomepage(userId)
            }
        }

        val handler = HomepageHandler(loadingScreenUiStateViewModel!!, homepagePresenter,
            onHomeInfoAvailable = { homePageInfo,->
                val viewHeight = calculateHomePageScreenHeight(homepageInfo = homePageInfo)
                homePageViewModel!!.setHomePageViewHeight(viewHeight)
                homePageViewModel!!.setHomePageInfo(homePageInfo)
                mainViewModel!!.setVendorBusinessLogoUrl(homePageInfo.vendorInfo!!.businessLogo!!)
            }, onDayAvailabilityInfoAvailable = {
                mainViewModel!!.setDayAvailability(it)
            })
        handler.init()

        val homepageInfo = homePageViewModel!!.homePageInfo.collectAsState()
        val homePageViewHeight = homePageViewModel!!.homePageViewHeight.collectAsState()
        val uiState = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()
        val vendorInfo = mainViewModel!!.connectedVendor.collectAsState()
        val userInfo = mainViewModel!!.currentUserInfo.value
        val hourOfDay = getHourOfDayDisplay(platformNavigator.getHourOfDay())

        if (homepageInfo.value.userInfo!!.userId != null){
            loadingScreenUiStateViewModel!!.switchScreenUIState(AppUIStates(isSuccess = true))
        }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )


            Scaffold(
                    snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
                    topBar = {
                        Row(modifier = Modifier.fillMaxWidth().height(60.dp), verticalAlignment = Alignment.CenterVertically) {
                            Row(modifier = Modifier.weight(4f).height(50.dp)) {
                              Box(modifier = Modifier.weight(0.7f).fillMaxHeight(), contentAlignment = Alignment.Center) {

                                  Box(Modifier.size(50.dp), contentAlignment = Alignment.Center) {
                                      Box(
                                          Modifier
                                              .size(50.dp)
                                              .background(color = Color.Transparent)
                                      ) {
                                          val modifier = Modifier
                                              .clip(CircleShape)
                                              .fillMaxSize()
                                          ImageComponent(imageModifier = modifier, imageRes = "drawable/main_icon.png", isAsync = false)
                                      }
                                  }

                              }
                                Column(modifier = Modifier.weight(3f).fillMaxHeight(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {

                                    Box(modifier = Modifier.fillMaxWidth().weight(1f)){
                                        TextComponent(
                                            text = "Hello ${userInfo.firstname}",
                                            fontSize = 18,
                                            fontFamily = GGSansRegular,
                                            textStyle = MaterialTheme.typography.h6,
                                            textColor = Color.Gray,
                                            textAlign = TextAlign.Left,
                                            fontWeight = FontWeight.ExtraBold,
                                            lineHeight = 30,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textModifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Box(modifier = Modifier.fillMaxWidth().weight(1f)){
                                        TextComponent(
                                            text = "Good $hourOfDay",
                                            fontSize = 20,
                                            fontFamily = GGSansSemiBold,
                                            textStyle = MaterialTheme.typography.h6,
                                            textColor = Colors.darkPrimary,
                                            textAlign = TextAlign.Left,
                                            fontWeight = FontWeight.ExtraBold,
                                            lineHeight = 30,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            textModifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                }
                            }
                            Box(modifier = Modifier.weight(1f).height(50.dp), contentAlignment = Alignment.Center) {
                                VendorLogo(imageUrl = vendorInfo.value.businessLogo!!, onVendorLogoClicked = {
                                    mainViewModel!!.setScreenNav(
                                        Pair(
                                            Screens.MAIN_SCREEN.toPath(),
                                            Screens.VENDOR_INFO_SCREEN.toPath()
                                        )
                                    )
                                })
                            }
                        }
                    },
                    content = {
                        Box(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                .background(color = Color.White),
                            contentAlignment = Alignment.TopStart
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
                                Box(
                                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                        .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                                        .background(
                                            color = Color.Transparent,
                                            shape = RoundedCornerShape(20.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    ErrorOccurredWidget(
                                        uiState.value.errorMessage,
                                        onRetryClicked = {
                                            if (homePageViewModel!!.homePageInfo.value.userInfo?.userId == null) {
                                                homepagePresenter.getUserHomepage(userId)
                                            }
                                        })
                                }
                            } else if (uiState.value.isSuccess) {
                                val recentAppointments = homepageInfo.value.recentAppointments
                                val vendorServices = homepageInfo.value.vendorServices
                                val vendorRecommendations =
                                    homepageInfo.value.recommendations
                                Column(
                                    Modifier
                                        .verticalScroll(state = rememberScrollState())
                                        .height(homePageViewHeight.value.dp)
                                        .fillMaxWidth()
                                        .padding(top = 5.dp, bottom = 70.dp)

                                ) {
                                    if (!vendorServices.isNullOrEmpty()) {

                                        ActionItemComponent(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(60.dp),
                                            buttonText = "Switch Vendor",
                                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                            fontSize = 20,
                                            textColor = Colors.darkPrimary,
                                            style = TextStyle(),
                                            iconRes = "drawable/switch.png",
                                            isDestructiveAction = false, onClick = {
                                                mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.CONNECT_VENDOR.toPath()))
                                            })

                                        AttachFeaturedServices()
                                        val servicesGridList = homepageInfo.value.servicesGridList!!
                                        val pagerState = rememberPagerState(pageCount = {servicesGridList.size})
                                        HorizontalPager(
                                            state = pagerState,
                                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                                            pageSpacing = 10.dp
                                        ) { page ->
                                            ServiceGridScreen(servicesGridList[page], onServiceSelected = {
                                                mainViewModel!!.setRecommendationServiceType(ServiceTypeItem())
                                                mainViewModel!!.setScreenNav(
                                                    Pair(
                                                        Screens.MAIN_SCREEN.toPath(),
                                                        Screens.BOOKING.toPath()
                                                    )
                                                )
                                                mainViewModel!!.setRecommendationServiceType(ServiceTypeItem())
                                                mainViewModel!!.setSelectedService(it)
                                            })
                                        }
                                        Row(
                                            Modifier
                                                .height(10.dp)
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            repeat(servicesGridList.size) { iteration ->
                                                val color: Color
                                                val width: Int
                                                if (pagerState.currentPage == iteration) {
                                                    color = Color.Black
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
                                    if (!vendorRecommendations.isNullOrEmpty()) {
                                        VendorRecommendation(vendorRecommendations, mainViewModel!!, onSeeAllRecommendations = {
                                            mainViewModel!!.setScreenNav(
                                                Pair(
                                                    Screens.MAIN_SCREEN.toPath(),
                                                    Screens.RECOMMENDATIONS_SCREEN.toPath()
                                                )
                                            )
                                        })
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

    @Composable
    fun ServiceGridScreen(vendorServices: List<Services>, onServiceSelected: (Services) -> Unit) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth().height(420.dp),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Start,
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
    fun AttachFeaturedServices(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Featured Services",
                    fontSize = 25,
                    fontFamily = GGSansBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = Modifier.fillMaxWidth(0.60f)
                )
                StraightLine()
            }
    }

    @Composable
    fun VendorRecommendation(recommendations: List<VendorRecommendation>, mainViewModel: MainViewModel, onSeeAllRecommendations:() -> Unit) {
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
                text = "Recommended",
                textModifier = Modifier.fillMaxWidth(0.50f),
                fontSize = 25,
                fontFamily = GGSansBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(1.dp)
                    .background(color =  Colors.lighterPrimaryColor)
            )

            Box(modifier = Modifier.fillMaxWidth().padding(end = 20.dp), contentAlignment = Alignment.CenterEnd){
                TextComponent(
                    text = "See All",
                    textModifier = Modifier.wrapContentWidth().clickable { onSeeAllRecommendations() },
                    fontSize = 18,
                    fontFamily = GGSansBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.primaryColor,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        RecommendedList(recommendations, mainViewModel)
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
                    fontSize = 25,
                    fontFamily = GGSansBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = Modifier.fillMaxWidth(0.70f)
                )
                StraightLine()
            }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun RecommendedList(recommendations: List<VendorRecommendation>, mainViewModel: MainViewModel) {
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
                    pageSpacing = 10.dp,
                    pageSize = PageSize.Fixed(300.dp)
                ) { page ->
                    VendorRecommendationsItem(recommendations[page],mainViewModel, onItemClickListener = {
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
                            color = Color.Black
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
}