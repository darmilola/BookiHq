package presentation.main.home

import GGSansRegular
import StackedSnackbarHost
import StackedSnakbarHostState
import domain.Models.BusinessWhatsAppStatusPage
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
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
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Models.Appointment
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.VendorRecommendation
import domain.Models.RecommendationType
import domain.Models.Screens
import domain.Models.Services
import domain.Models.Vendor
import domain.Models.VendorStatusModel
import org.koin.core.component.KoinComponent
import presentation.components.StraightLine
import presentation.Products.ProductDetailBottomSheet
import presentation.Products.HomeProductItem
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import utils.getAppointmentViewHeight
import presentation.widgets.BusinessStatusItemWidget
import presentation.widgets.BusinessStatusWidgetUpdated
import presentation.widgets.HomeServicesWidget
import presentation.widgets.NewAppointmentWidget
import presentation.widgets.RecommendedServiceItem
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.getPopularProductViewHeight

class HomeTab(private val homePageViewModel: HomePageViewModel,
              private val mainViewModel: MainViewModel) : Tab, KoinComponent {
    private val preferenceSettings: Settings = Settings()
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
         val userInfo = homePageViewModel.homePageInfo.value.homepageModel.userInfo
         val vendorInfo = homePageViewModel.homePageInfo.value.homepageModel.vendorInfo
         val vendorStatus = homePageViewModel.homePageInfo.value.homepageModel.vendorStatus
         val vendorServices = homePageViewModel.homePageInfo.value.homepageModel.vendorServices
         val specialistInfo = homePageViewModel.homePageInfo.value.homepageModel.specialistInfo
         val popularProducts = homePageViewModel.homePageInfo.value.homepageModel.popularProducts
         val recentAppointments = homePageViewModel.homePageInfo.value.homepageModel.recentAppointment
         val vendorRecommendations = homePageViewModel.homePageInfo.value.homepageModel.recommendationRecommendations

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (userInfo != null) {
            mainViewModel.setUserInfo(userInfo)
            setCityId(userInfo.cityId!!)
            setCountryId(userInfo.countryId!!)
        }
        if (vendorInfo != null) {
            mainViewModel.setConnectedVendor(vendorInfo)
        }
        if (specialistInfo != null) {
            mainViewModel.setSpecialistInfo(specialistInfo)
        }
            val columnModifier = Modifier
                .padding(top = 5.dp)
                .fillMaxSize()

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.White)

                ) {
                    Column(Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                        if (vendorInfo != null) {
                            AttachBusinessName(vendorInfo)
                        }
                        if (vendorStatus != null) {
                            BusinessStatusDisplay(vendorStatus)
                        }
                        AttachOurServices()
                        if (vendorServices != null) {
                            ServiceGridScreen(vendorServices)
                        }
                        if (vendorRecommendations != null) {
                            RecommendedSessions(vendorRecommendations)
                        }

                        PopularProducts()

                        if (popularProducts != null) {
                            PopularProductScreen(popularProducts,mainViewModel, stackedSnackBarHostState)
                        }
                        AttachAppointments()
                        RecentAppointmentScreen(
                            appointmentList = recentAppointments
                        )
                    }
                }
            }
        })
    }



    @Composable
    fun ServiceGridScreen(vendorServices: List<Services>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().height(280.dp),
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
    fun RecommendedSessions(recommendations: List<VendorRecommendation>){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
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

            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(getPopularProductViewHeight(popularProducts).dp),
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

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun RecommendedAppointmentList(recommendations: List<VendorRecommendation>) {
        val pagerState = rememberPagerState(pageCount = {
            recommendations.size
        })

        val boxModifier =
            Modifier
                .background(color = Color.White)
                .height(410.dp)
                .fillMaxWidth()
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
                                mainViewModel.setVendorRecommendation(it)
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
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .height(getAppointmentViewHeight(appointmentList).dp), userScrollEnabled = false
            ) {
                items(key = { it -> it.appointmentId!!}, items =  appointmentList) { item ->
                    NewAppointmentWidget(item)
                }

            }
        }
    }

    @Composable
    fun AttachBusinessName(vendor: Vendor){
        val rowModifier = Modifier
            .padding(start = 10.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier,
            ) {
                val modifier = Modifier.padding(start = 3.dp)
                AttachLocationIcon()
                TextComponent(
                    text = vendor.businessName,
                    fontSize = 16,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    textModifier = modifier
                )
            }
        }

    @Composable
    fun GetBusinessPageList(statusList: List<VendorStatusModel>) : List<BusinessWhatsAppStatusPage> {
         val adsList: ArrayList<BusinessWhatsAppStatusPage> = arrayListOf()
         val imageWidget = BusinessStatusItemWidget()
        for (item in statusList){
            val statusAdsPage = BusinessWhatsAppStatusPage(statusWidget = imageWidget, imageUrl = item.imageUrl)
            adsList.add(statusAdsPage)
         }
         return adsList
    }

    @Composable
    fun BusinessStatusDisplay(statusList: List<VendorStatusModel>) {
        val boxBgModifier =
            Modifier
                .fillMaxHeight(0.50f)
                .fillMaxWidth()

        Box(modifier = boxBgModifier) {
            val businessPageList = GetBusinessPageList(statusList)
            BusinessStatusWidgetUpdated(businessPageList)
        }
    }


    @Composable
    fun AttachLocationIcon() {
        val modifier = Modifier
            .size(16.dp)
        ImageComponent(imageModifier = modifier, imageRes = "drawable/location_icon_filled.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
    }

    private fun setCityId(cityId: Int) {
        preferenceSettings["cityId"] = cityId
    }

    private fun setCountryId(countryId: Int) {
        preferenceSettings["countryId"] = countryId
    }

}