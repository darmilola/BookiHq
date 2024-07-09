package presentation.home

import GGSansRegular
import StackedSnackbarHost
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import applications.device.ScreenSizeInfo
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.Models.Appointment
import domain.Enums.AppointmentType
import domain.Models.HomepageInfo
import domain.Models.VendorRecommendation
import domain.Enums.RecommendationType
import domain.Enums.Screens
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.Services
import domain.Models.StatusImageModel
import domain.Models.VendorStatusModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.HomepageHandler
import presentation.components.StraightLine
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.ShopStatusWidget
import presentation.widgets.HomeServicesWidget
import presentation.widgets.MeetingAppointmentWidget
import presentation.widgets.RecommendedServiceItem
import presentation.widgets.AppointmentWidget
import presentation.widgets.ProductDetailBottomSheet
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.calculateHomePageScreenHeight
import utils.getRecentAppointmentViewHeight
import utils.getServicesViewHeight
import utils.pxToDp

@Parcelize
class HomeTab() : Tab, KoinComponent, Parcelable {

    private var uiStateViewModel: UIStateViewModel? = null
    private val homepagePresenter: HomepagePresenter by inject()
    private var userId: Long = -1L
    private val preferenceSettings: Settings = Settings()
    private var availabilityActionUIStateViewModel: ActionUIStateViewModel? = null
    private var mainViewModel: MainViewModel? = null
    private var homePageViewModel: HomePageViewModel? = null

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


    @Composable
    override fun Content() {
        userId = preferenceSettings["profileId", -1L]
        val isStatusViewExpanded = remember { mutableStateOf(false) }

        val screenSizeInfo = ScreenSizeInfo()
        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
            if (availabilityActionUIStateViewModel == null) {
                availabilityActionUIStateViewModel = kmpViewModel(
                    factory = viewModelFactory {
                        ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                    },
                )
            }


            val handler = HomepageHandler(uiStateViewModel!!, homepagePresenter,
                onHomeInfoAvailable = { homePageInfo, vendorStatus ->
                    val viewHeight = calculateHomePageScreenHeight(
                        homepageInfo = homePageInfo,
                        screenSizeInfo = screenSizeInfo,
                        isStatusExpanded = false
                    )
                    homePageViewModel!!.setHomePageViewHeight(viewHeight)
                    homePageViewModel!!.setHomePageInfo(homePageInfo)
                    homePageViewModel!!.setVendorStatus(vendorStatus)
                    mainViewModel!!.setConnectedVendor(homePageInfo.vendorInfo!!)
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

            if (homePageViewModel!!.homePageInfo.value.userInfo == null) {
                homepagePresenter.getUserHomepage(userId, "2348022510893")
            }
        }

        val uiState = uiStateViewModel!!.uiStateInfo.collectAsState()
        val homepageInfo = homePageViewModel!!.homePageInfo.collectAsState()
        val mVendorStatus = homePageViewModel!!.vendorStatus.collectAsState()
        val homePageViewHeight = homePageViewModel!!.homePageViewHeight.collectAsState()

        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.value.loadingVisible) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    IndeterminateCircularProgressBar()
                }
            } else if (uiState.value.errorOccurred) {

                val message = uiState.value.errorMessage

                //Error Occurred

            } else if (uiState.value.contentVisible) {
                val recentAppointments = homepageInfo.value.recentAppointment
                val vendorServices = homepageInfo.value.vendorServices
                val vendorRecommendations = homepageInfo.value.recommendationRecommendations

                val stackedSnackBarHostState = rememberStackedSnackbarHostState(
                    maxStack = 5,
                    animation = StackedSnackbarAnimation.Bounce
                )

                Scaffold(
                    snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
                    topBar = {},
                    floatingActionButton = {},
                    content = {
                        Column(
                            Modifier
                                .verticalScroll(state = rememberScrollState())
                                .height(homePageViewHeight.value.dp)
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                                .fillMaxWidth()

                        ) {
                            BusinessStatusDisplay(
                                onViewHeightChanged = { _: Float, isStatusExpanded: Boolean ->
                                    isStatusViewExpanded.value = isStatusExpanded
                                })

                            AttachOurServices()
                            if (vendorServices != null) {
                                ServiceGridScreen(vendorServices)
                            }
                            if (vendorRecommendations != null) {
                                RecommendedSessions(vendorRecommendations, mainViewModel!!)
                            }
                            AttachAppointments()
                            RecentAppointmentScreen(appointmentList = recentAppointments)
                        }
                    })
            }
        }
    }

    private fun saveAccountInfoFromServer(homePageInfo: HomepageInfo){
        val preferenceSettings = Settings()
        preferenceSettings["userEmail"] = homePageInfo.userInfo?.email
        preferenceSettings["profileId"] = homePageInfo.userInfo?.userId
        preferenceSettings["userFirstname"] = homePageInfo.userInfo?.firstname
        preferenceSettings["vendorEmail"] = homePageInfo.vendorInfo?.businessEmail
        preferenceSettings["vendorId"] = homePageInfo.vendorInfo?.vendorId
        preferenceSettings["vendorBusinessLogoUrl"] = homePageInfo.vendorInfo?.businessLogo
    }

    @Composable
    fun ServiceGridScreen(vendorServices: List<Services>) {
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
                HomeServicesWidget(vendorServices[it], mainViewModel!!)
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
    fun AttachAppointments(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 20.dp, bottom = 20.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Recently",
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

        println("Called $showProductDetailBottomSheet")

        if (showProductDetailBottomSheet) {
            ProductDetailBottomSheet(
                mainViewModel,
                isViewedFromCart = false,
                OrderItem(itemProduct = selectedProduct.value),
                onDismiss = { isAddToCart, item ->
                    showProductDetailBottomSheet = false
                },
                onRemoveFromCart = {})
        }


        val boxModifier =
            Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(start = 5.dp)

        val boxBgModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()

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
                    RecommendedServiceItem(recommendations[page], onItemClickListener = {
                        when (it.recommendationType) {
                            RecommendationType.Services.toPath() -> {
                                mainViewModel.setScreenNav(
                                    Pair(
                                        Screens.MAIN_TAB.toPath(),
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
    fun RecentAppointmentScreen(appointmentList: List<Appointment>?) {
        if (appointmentList != null) {
            val viewHeight = getRecentAppointmentViewHeight(appointmentList)
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .height(viewHeight.dp), userScrollEnabled = false
            ) {
                items(key = { it -> it.appointmentId!!}, items =  appointmentList) { item ->
                    if (item.appointmentType == AppointmentType.MEETING.toPath()) {
                        MeetingAppointmentWidget(
                            appointment = item,
                            appointmentPresenter = null,
                            isFromHomeTab = true
                        )
                    } else {
                        AppointmentWidget(
                            item,
                            appointmentPresenter = null,
                            postponementViewModel = null,
                            availabilityActionUIStateViewModel!!,
                            isFromHomeTab = true
                        )
                    }
                }

            }
        }
    }


    @Composable
    fun BusinessStatusDisplay(onViewHeightChanged: (Float, Boolean) -> Unit) {
        val isStatusExpanded = remember { mutableStateOf(false) }
        val statusList = arrayListOf<VendorStatusModel>()

        val statusModel1 = VendorStatusModel("",1L, StatusImageModel(imageId = "",
            imageUrl = "https://cdn.pixabay.com/photo/2018/07/13/19/56/st-peters-church-3536449_1280.jpg", caption = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))
        val statusModel2 = VendorStatusModel("",1L, StatusImageModel(imageId = "", imageUrl = "https://cdn.pixabay.com/photo/2018/07/13/19/56/st-peters-church-3536449_1280.jpg",
            caption = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))
        val statusModel3 = VendorStatusModel("",1L, StatusImageModel(imageId = "", imageUrl = "https://cdn.pixabay.com/photo/2018/07/13/19/56/st-peters-church-3536449_1280.jpg",
            caption = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))
        val statusModel4 = VendorStatusModel("",1L, StatusImageModel(imageId = "", imageUrl = "https://cdn.pixabay.com/photo/2018/07/13/19/56/st-peters-church-3536449_1280.jpg"))
        val statusModel5 = VendorStatusModel("",1L, StatusImageModel(imageId = "", imageUrl = "https://cdn.pixabay.com/photo/2018/07/13/19/56/st-peters-church-3536449_1280.jpg"))

        statusList.add(statusModel1)
        statusList.add(statusModel2)
        statusList.add(statusModel3)
        statusList.add(statusModel4)
        statusList.add(statusModel5)


        val screenSizeInfo = ScreenSizeInfo()
        val percentChangeExpanded =  1f
        val percentChangeCollapsed = 0.7f


        val heightAtExpanded = (screenSizeInfo.heightPx * percentChangeExpanded)
        val heightAtCollapsed = screenSizeInfo.heightPx * percentChangeCollapsed


        val heightChange: Float by animateFloatAsState(targetValue = if (isStatusExpanded.value) heightAtExpanded else heightAtCollapsed,
            animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
        )

        onViewHeightChanged(heightChange, isStatusExpanded.value)
        val modifier =
            Modifier.fillMaxWidth()
                .height(heightChange.toInt().pxToDp())
                .background(color = Color.White)
        Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
            ShopStatusWidget(statusList, onStatusViewChanged = {
                    isStatusViewExpanded -> isStatusExpanded.value = isStatusViewExpanded
            })
        }
    }
}