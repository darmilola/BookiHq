package presentation.main.home

import GGSansRegular
import StackedSnackbarHost
import StackedSnakbarHostState
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import applications.device.ScreenSizeInfo
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.Models.Appointment
import domain.Models.AppointmentType
import domain.Models.HomepageInfo
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.VendorRecommendation
import domain.Models.RecommendationType
import domain.Models.Screens
import domain.Models.Services
import domain.Models.VendorStatusModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.StraightLine
import presentation.Products.HomeProductItem
import presentation.Products.ProductDetailContent
import presentation.components.FloatingActionButton
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ScreenUIStateViewModel
import presentation.viewmodels.ScreenUIStates
import presentation.widgets.BusinessWhatsAppStatusWidget
import presentation.widgets.HomeServicesWidget
import presentation.widgets.MeetingAppointmentWidget
import presentation.widgets.RecommendedServiceItem
import presentation.widgets.AppointmentWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.calculateHomePageScreenHeight
import utils.getPercentOfScreenHeight
import utils.getPopularProductViewHeight
import utils.getRecentAppointmentViewHeight
import utils.getServicesViewHeight

class HomeTab(private val mainViewModel: MainViewModel, private val homePageViewModel: HomePageViewModel) : Tab, KoinComponent {

    private var screenUiStateViewModel: ScreenUIStateViewModel? = null
    private val homepagePresenter: HomepagePresenter by inject()
    private var userEmail: String = ""
    private val preferenceSettings: Settings = Settings()
    private var availabilityActionUIStateViewModel: ActionUIStateViewModel? = null

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



    @Composable
    override fun Content() {
        userEmail = preferenceSettings["userEmail", "devprocess0@gmail.com"]

        val screenSizeInfo = ScreenSizeInfo()
        if (screenUiStateViewModel == null) {
            screenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
            if (availabilityActionUIStateViewModel == null) {
                availabilityActionUIStateViewModel = kmpViewModel(
                    factory = viewModelFactory {
                        ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                    },
                )
            }


            val handler = HomePageHandler(screenUiStateViewModel!!, homepagePresenter,
                 onHomeInfoAvailable = {
                        homePageInfo, vendorStatus ->
                    val viewHeight = calculateHomePageScreenHeight(homepageInfo = homePageInfo, screenSizeInfo = screenSizeInfo, isStatusExpanded = false)
                    homePageViewModel.setHomePageViewHeight(viewHeight)
                    homePageViewModel.setHomePageInfo(homePageInfo)
                    homePageViewModel.setVendorStatus(vendorStatus)
                    mainViewModel.setConnectedVendor(homePageInfo.vendorInfo!!)
                    mainViewModel.setUserEmail(homePageInfo.userInfo?.userEmail!!)
                    mainViewModel.setUserFirstname(homePageInfo.userInfo.firstname!!)
                    mainViewModel.setUserId(homePageInfo.userInfo.userId!!)
                    mainViewModel.setVendorEmail(homePageInfo.vendorInfo.businessEmail)
                    mainViewModel.setVendorId(homePageInfo.vendorInfo.vendorId!!)
                    mainViewModel.setVendorBusinessLogoUrl(homePageInfo.vendorInfo.businessLogo)
                    mainViewModel.setUserInfo(homePageInfo.userInfo)
                    mainViewModel.setSpecialistId(homePageInfo.specialistInfo?.id!!)
                    saveAccountInfoFromServer(homePageInfo)
                })
            handler.init()

            if (homePageViewModel.homePageInfo.value.userInfo == null) {
                homepagePresenter.getUserHomepage(userEmail, "2348111343996")
            }
        }

        val uiState = screenUiStateViewModel!!.uiStateInfo.collectAsState()
        val homepageInfo = homePageViewModel.homePageInfo.collectAsState()
        val mVendorStatus = homePageViewModel.vendorStatus.collectAsState()
        val homePageViewHeight = homePageViewModel.homePageViewHeight.collectAsState()
        val isStatusViewExpanded = remember { mutableStateOf(false) }


        if (uiState.value.loadingVisible) {
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IndeterminateCircularProgressBar()
            }
        }

        else if (uiState.value.errorOccurred) {

            val message = uiState.value.errorMessage

            //Error Occurred

        }

        else if (uiState.value.contentVisible) {
                val popularProducts = homepageInfo.value.popularProducts
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
                    floatingActionButton = {
                        if (isStatusViewExpanded.value) {

                            val buttonStyle = Modifier
                                .padding(bottom = 60.dp)
                                .clip(CircleShape)
                                .size(60.dp)
                            FloatingActionButton(
                                modifier = buttonStyle,
                                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                                iconRes = "drawable/new_chat_icon.png",
                                colorFilter = ColorFilter.tint(color = Color.White)
                            )
                        }
                    },
                     content = {
                               Column(
                                    Modifier
                                        .verticalScroll(state = rememberScrollState())
                                        .height(homePageViewHeight.value.dp)
                                        .padding(top = 5.dp)
                                        .background(color = Color.White)
                                        .fillMaxWidth()

                                ) {
                                     BusinessStatusDisplay(mVendorStatus.value, onViewHeightChanged = {
                                             newHeight: Int, isStatusExpanded: Boolean ->
                                             isStatusViewExpanded.value = isStatusExpanded })

                                    AttachOurServices()
                                    if (vendorServices != null) {
                                        ServiceGridScreen(vendorServices)
                                    }
                                    if (vendorRecommendations != null) {
                                        RecommendedSessions(vendorRecommendations)
                                    }

                                    PopularProducts()
                                    if (popularProducts != null) {
                                        PopularProductScreen(
                                            popularProducts,
                                            mainViewModel,
                                            stackedSnackBarHostState)
                                    }
                                    AttachAppointments()
                                    RecentAppointmentScreen(appointmentList = recentAppointments)
                                }
                     })
           }
    }

    private fun saveAccountInfoFromServer(homePageInfo: HomepageInfo){
        val preferenceSettings = Settings()
        preferenceSettings["userEmail"] = homePageInfo.userInfo?.userEmail
        preferenceSettings["userId"] = homePageInfo.userInfo?.userId
        preferenceSettings["userFirstname"] = homePageInfo.userInfo?.firstname
        preferenceSettings["vendorEmail"] = homePageInfo.vendorInfo?.businessEmail
        preferenceSettings["vendorId"] = homePageInfo.vendorInfo?.vendorId
        preferenceSettings["specialistId"] = homePageInfo.specialistInfo?.id
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
                HomeServicesWidget(vendorServices[it], mainViewModel)
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
    fun RecommendedSessions(recommendations: List<VendorRecommendation>) {
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
        RecommendedAppointmentList(recommendations)
    }

    }


    @Composable
    fun AttachAppointments(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 10.dp, bottom = 20.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Recent Appointments",
                    fontSize = 16,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 10,
                    textModifier = Modifier.fillMaxWidth(0.40f)
                )
                StraightLine()
            }
    }


    @Composable
    fun PopularProducts(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 30.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Popular Products",
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 20,
                    textModifier = Modifier.fillMaxWidth(0.42f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                StraightLine()
            }

        }




    @Composable
    fun PopularProductScreen(popularProducts: List<Product>, mainViewModel: MainViewModel, stackedSnackBarHostState: StackedSnakbarHostState) {

        Column {

            var showProductDetailBottomSheet by remember { mutableStateOf(false) }
            val selectedProduct  = remember { mutableStateOf(Product()) }

            if (showProductDetailBottomSheet) {
                ProductDetailBottomSheet(mainViewModel,isViewedFromCart = false, OrderItem(itemProduct = selectedProduct.value), onDismiss = {
                        isAddToCart,item -> if (isAddToCart){
                    ShowSnackBar(title = "Successful",
                        description = "Your Product has been successfully Added to Cart",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.SUCCESS,
                        stackedSnackBarHostState,
                        onActionClick = {})
                     }
                    showProductDetailBottomSheet = false

                }, onRemoveFromCart = {})
            }

            val viewHeight = getPopularProductViewHeight(popularProducts)

            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(viewHeight.dp),
                contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                userScrollEnabled = false
            ) {
                items(popularProducts.size) {
                    HomeProductItem(popularProducts[it],onProductClickListener = { it2 ->
                        selectedProduct.value = it2
                        showProductDetailBottomSheet = true
                    })
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductDetailBottomSheet(mainViewModel: MainViewModel, isViewedFromCart: Boolean = false, selectedProduct: OrderItem, onDismiss: (isAddToCart: Boolean, OrderItem) -> Unit, onRemoveFromCart: (OrderItem) -> Unit) {
        val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            modifier = Modifier.padding(top = 20.dp),
            onDismissRequest = { onDismiss(false, selectedProduct) },
            sheetState = modalBottomSheetState,
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            containerColor = Color(0xFFF3F3F3),
            dragHandle = {},
        ) {
            ProductDetailContent(mainViewModel,isViewedFromCart,selectedProduct, onAddToCart = {
                onDismiss(it,selectedProduct)
            }, onRemoveFromCart = {
                onRemoveFromCart(it)
            })
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun RecommendedAppointmentList(recommendations: List<VendorRecommendation>) {
        val pagerState = rememberPagerState(pageCount = {
            recommendations.size
        })

        val boxModifier =
            Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(start = 5.dp)

        val boxBgModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()


        Box(modifier = boxBgModifier) {
            var showProductBottomSheet by remember { mutableStateOf(false) }


            Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    pageSpacing = 10.dp
                ) { page ->
                    RecommendedServiceItem (recommendations[page], onItemClickListener = {
                        when (it.recommendationType) {
                            RecommendationType.Services.toPath() -> {
                                mainViewModel.setScreenNav(Pair(Screens.MAIN_TAB.toPath(), Screens.BOOKING.toPath()))
                                mainViewModel.setSelectedService(it.serviceTypeItem?.serviceDetails!!)
                                //mainViewModel.setVendorRecommendation(it)
                            }
                            RecommendationType.Products.toPath() -> {
                                showProductBottomSheet = true
                            }
                        }
                    })
                }
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
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
                                .height(3.dp)
                                .width(width.dp)
                        )
                    }

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


    class HomePageHandler(
        private val screenUiStateViewModel: ScreenUIStateViewModel,
        private val homepagePresenter: HomepagePresenter,
        private val onHomeInfoAvailable: (HomepageInfo, ArrayList<VendorStatusModel>) -> Unit) : HomepageContract.View {
        fun init() {
            homepagePresenter.registerUIContract(this)
        }

        override fun showLce(uiState: ScreenUIStates) {
            screenUiStateViewModel.switchScreenUIState(uiState)
        }
        override fun showHome(homePageInfo: HomepageInfo, vendorStatus: ArrayList<VendorStatusModel>) {
            onHomeInfoAvailable(homePageInfo, vendorStatus)
        }
    }



    @Composable
    fun BusinessStatusDisplay(statusList: List<VendorStatusModel>, onViewHeightChanged: (Int, Boolean) -> Unit) {
        val isStatusExpanded = remember { mutableStateOf(false) }
        val isWidthGreaterThanHeight = remember { mutableStateOf(false) }
        val screenSizeInfo = ScreenSizeInfo()
        val percentChangeExpanded = if (isWidthGreaterThanHeight.value) 70 else 90
        val percentChangeCollapsed = if (isWidthGreaterThanHeight.value) 50 else 70

        val heightAtExpanded = getPercentOfScreenHeight(screenSizeInfo.heightDp, percentChange = percentChangeExpanded)
        val heightAtCollapsed = getPercentOfScreenHeight(screenSizeInfo.heightDp, percentChange = percentChangeCollapsed)

        val heightChange: Float by animateFloatAsState(targetValue = if (isStatusExpanded.value) heightAtExpanded.toFloat() else heightAtCollapsed.toFloat(),
            animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
        )
        val viewHeight =  if (isStatusExpanded.value) heightAtExpanded else heightAtCollapsed
        onViewHeightChanged(viewHeight, isStatusExpanded.value)
        val modifier =
            Modifier.fillMaxWidth()
                .height(heightChange.dp)
                .background(color = Color.White)
        Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
            BusinessWhatsAppStatusWidget(statusList, onStatusViewChanged = {
                    isStatusViewExpanded -> isStatusExpanded.value = isStatusViewExpanded
            }, onWidthGreaterThanHeight = {
                 isWidthGreaterThanHeight.value = it
            })
        }
    }
}