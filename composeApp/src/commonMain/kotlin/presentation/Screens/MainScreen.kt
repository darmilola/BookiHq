package presentation.Screens

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import dev.icerock.moko.parcelize.Parcelize
import domain.Enums.MainTabEnum
import domain.Enums.Screens
import domain.Enums.SharedPreferenceEnum
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.Orders.Orders
import presentation.Packages.Packages
import presentation.Products.Cart
import presentation.Products.ShopProductTab
import presentation.account.AccountTab
import presentation.account.JoinASpa
import presentation.appointments.AppointmentsTab
import presentation.authentication.AuthenticationPresenter
import presentation.appointmentBookings.BookingScreen
import presentation.connectVendor.SwitchVendor
import presentation.consultation.VirtualConsultationRoom
import presentation.home.HomeTab
import presentation.main.MainScreenTabs
import presentation.main.MainTopBar
import presentation.profile.EditProfile
import presentation.therapist.TherapistDashboard
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import theme.styles.Colors
import utils.ParcelableScreen
@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class MainScreen(private val platformNavigator: PlatformNavigator): KoinComponent, ParcelableScreen, ScreenTransition {

    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private val preferenceSettings: Settings = Settings()
    private var userId: Long = 0L
    @Transient
    private var mainScreenTabs: MainScreenTabs? = null
    @Transient
    private val authenticationPresenter: AuthenticationPresenter by inject()
    @Transient
    private var screenNav: State<Pair<Int, Int>>? = null
    @Transient
    private var homeTab: HomeTab? = null
    @Transient
    private var shopProductTab: ShopProductTab? = null
    @Transient
    private var appointmentsTab: AppointmentsTab? = null
    @Transient
    private var packages: Packages? = null
    @Transient
    private var accountTab: AccountTab? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }


    @Composable
    override fun Content() {
        userId = preferenceSettings.getLong(SharedPreferenceEnum.PROFILE_ID.toPath(), 0L)
        platformNavigator.startNotificationService {
            authenticationPresenter.updateFcmToken(userId = userId, fcmToken = it)
        }
        screenNav = mainViewModel?.screenNav?.collectAsState()
        val restartApp = mainViewModel!!.restartApp.collectAsState()

        var homePageViewModel: HomePageViewModel? = null

        if (homePageViewModel == null) {
            homePageViewModel = kmpViewModel(
                factory = viewModelFactory {
                    HomePageViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            platformNavigator.exitApp()
        }

        var isBottomNavSelected by remember { mutableStateOf(true) }
        val userInfo = mainViewModel!!.currentUserInfo.collectAsState()
        val vendorInfo = mainViewModel!!.connectedVendor.collectAsState()

        if (restartApp.value) {
            mainViewModel!!.setRestartApp(false)
            val navigator = LocalNavigator.currentOrThrow
            val splashScreen = SplashScreen(platformNavigator = platformNavigator)
            splashScreen.setDatabaseBuilder(databaseBuilder)
            splashScreen.setMainViewModel(mainViewModel!!)
            navigator.replaceAll(splashScreen)
        }

        when (screenNav?.value?.second) {
            Screens.BOOKING.toPath() -> {
                val bookingTab = BookingScreen(platformNavigator)
                bookingTab.setMainViewModel(mainViewModel!!)
                bookingTab.setDatabaseBuilder(databaseBuilder)
                val nav = LocalNavigator.currentOrThrow
                nav.push(bookingTab)
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.BOOKING.toPath(),
                        Screens.DEFAULT.toPath()
                    )
                )
            }

            Screens.ORDERS.toPath() -> {
                val orders = Orders()
                orders.setMainViewModel(mainViewModel!!)
                val nav = LocalNavigator.currentOrThrow
                nav.push(orders)
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.ORDERS.toPath(),
                        Screens.DEFAULT.toPath()
                    )
                )
            }

            Screens.CONNECT_VENDOR.toPath() -> {
                val switchVendor = SwitchVendor(platformNavigator)
                switchVendor.setMainViewModel(mainViewModel!!)
                switchVendor.setDatabaseBuilder(databaseBuilder)
                val nav = LocalNavigator.currentOrThrow
                nav.push(switchVendor)
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.CONNECT_VENDOR.toPath(),
                        Screens.DEFAULT.toPath()
                    )
                )
            }
            Screens.CONNECTED_VENDOR_DETAILS.toPath() -> {
                val connectedVendorDetails = ConnectVendorDetailsScreen(vendorInfo.value,platformNavigator)
                connectedVendorDetails.setMainViewModel(mainViewModel!!)
                val nav = LocalNavigator.currentOrThrow
                nav.push(connectedVendorDetails)
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.CONNECTED_VENDOR_DETAILS.toPath(),
                        Screens.DEFAULT.toPath()
                    )
                )
            }
            Screens.JOIN_SPA.toPath() -> {
                val joinASpa = JoinASpa(platformNavigator)
                joinASpa.setMainViewModel(mainViewModel!!)
                val nav = LocalNavigator.currentOrThrow
                nav.push(joinASpa)
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.JOIN_SPA.toPath(),
                        Screens.DEFAULT.toPath()
                    )
                )
            }

            Screens.EDIT_PROFILE.toPath() -> {
                val editProfile = EditProfile(platformNavigator)
                editProfile.setMainViewModel(mainViewModel!!)
                editProfile.setDatabaseBuilder(databaseBuilder)
                val nav = LocalNavigator.currentOrThrow
                nav.push(editProfile)
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.EDIT_PROFILE.toPath(),
                        Screens.DEFAULT.toPath()
                    )
                )
            }

            Screens.CART.toPath() -> {
                val cart = Cart(platformNavigator)
                cart.setMainViewModel(mainViewModel!!)
                cart.setDatabaseBuilder(databaseBuilder)
                val nav = LocalNavigator.currentOrThrow
                nav.push(cart)
                mainViewModel!!.setScreenNav(Pair(Screens.CART.toPath(), Screens.DEFAULT.toPath()))
            }
            Screens.CONSULTATION_ROOM.toPath() -> {
                val consultation = VirtualConsultationRoom()
                consultation.setMainViewModel(mainViewModel!!)
                val nav = LocalNavigator.currentOrThrow
                nav.push(consultation)
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.CONSULTATION_ROOM.toPath(),
                        Screens.DEFAULT.toPath()
                    )
                )
            }

            Screens.THERAPIST_DASHBOARD.toPath() -> {
                val dashboard = TherapistDashboard()
                dashboard.setMainViewModel(mainViewModel!!)
                dashboard.setTherapistInfo(homePageViewModel.homePageInfo.value.therapistInfo)
                val nav = LocalNavigator.currentOrThrow
                nav.push(dashboard)
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.THERAPIST_DASHBOARD.toPath(),
                        Screens.DEFAULT.toPath()
                    )
                )
            }
        }

        TabNavigator(showDefaultTab(mainViewModel!!, homePageViewModel)) {
                it2 ->
            Scaffold(
                topBar = {
                    if (userInfo.value.userId != null && vendorInfo.value.vendorId != null) {
                        MainTopBar(mainViewModel!!)
                    }

                },
                content = {
                    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                        CurrentTab()

                    }
                },
                backgroundColor = Color.White,
                bottomBar = {
                    if (userInfo.value.userId != null && vendorInfo.value.vendorId != null) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            BottomNavigation(
                                modifier = Modifier.height(60.dp)
                                    .padding(start = 10.dp, end = 10.dp)
                                    .background(
                                        shape = RoundedCornerShape(15.dp),
                                        color = Colors.darkPrimary
                                    ),
                                backgroundColor = Color.Transparent,
                                elevation = 0.dp
                            )
                            {
                                homeTab = HomeTab(platformNavigator)
                                homeTab!!.setMainViewModel(mainViewModel!!)
                                homeTab!!.setHomePageViewModel(homePageViewModel)
                                TabNavigationItem(
                                    homeTab!!,
                                    selectedImage = "drawable/home_icon.png",
                                    unselectedImage = "drawable/home_outline.png",
                                    labelText = "Home",
                                    imageSize = 22,
                                    currentTabId = 0,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                                shopProductTab = ShopProductTab()
                                shopProductTab!!.setMainViewModel(mainViewModel!!)
                                shopProductTab!!.setDatabaseBuilder(databaseBuilder)
                                TabNavigationItem(
                                    shopProductTab!!,
                                    selectedImage = "drawable/shopping_basket.png",
                                    unselectedImage = "drawable/shopping_basket_outline.png",
                                    labelText = "Shop",
                                    imageSize = 22,
                                    currentTabId = 1,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                                packages = Packages()
                                packages!!.setMainViewModel(mainViewModel!!)
                                TabNavigationItem(
                                    packages!!,
                                    selectedImage = "drawable/package_icon_filled.png",
                                    unselectedImage = "drawable/package_icon.png",
                                    labelText = "Packages",
                                    imageSize = 26,
                                    currentTabId = 2,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                                appointmentsTab = AppointmentsTab(platformNavigator)
                                appointmentsTab!!.setMainViewModel(mainViewModel!!)
                                TabNavigationItem(
                                    appointmentsTab!!,
                                    selectedImage = "drawable/appointment_icon.png",
                                    unselectedImage = "drawable/appointment_outline.png",
                                    labelText = "History",
                                    imageSize = 25,
                                    currentTabId = 3,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                                accountTab = AccountTab()
                                accountTab!!.setMainViewModel(mainViewModel!!)
                                TabNavigationItem(
                                    accountTab!!,
                                    selectedImage = "drawable/more_circle_filled_icon.png",
                                    unselectedImage = "drawable/more_icon_outlined.png",
                                    labelText = "More",
                                    imageSize = 25,
                                    currentTabId = 4,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                            }
                        }
                    }
                }
            )
        }
    }


    private fun showDefaultTab(mainViewModel: MainViewModel, homePageViewModel: HomePageViewModel): HomeTab {
        homeTab = HomeTab(platformNavigator)
        homeTab!!.setMainViewModel(mainViewModel)
        homeTab!!.setHomePageViewModel(homePageViewModel)

        return  homeTab!!
    }
    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab, selectedImage: String, unselectedImage: String, imageSize: Int = 30, labelText: String, currentTabId: Int = 0, tabNavigator: TabNavigator, mainViewModel: MainViewModel, onBottomNavSelected:() -> Unit) {
        var imageStr by remember { mutableStateOf(unselectedImage) }
        var imageTint by remember { mutableStateOf(Color.White) }
        var handleTint by remember { mutableStateOf(Colors.darkPrimary) }

        if (tabNavigator.current is HomeTab && currentTabId == MainTabEnum.HOME.toPageID()) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "Home"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setDisplayedTab(MainTabEnum.HOME.toPath())
        }
        else  if (tabNavigator.current is ShopProductTab && currentTabId == MainTabEnum.PRODUCTS.toPageID()) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "Products"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setIsClickedSearchProduct(false)
            mainViewModel.setDisplayedTab(MainTabEnum.PRODUCTS.toPath())
        }
        else if (tabNavigator.current is Packages && currentTabId == MainTabEnum.PACKAGES.toPageID()) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "Packages"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setDisplayedTab(MainTabEnum.PACKAGES.toPath())

        }
        else if (tabNavigator.current is AppointmentsTab && currentTabId == MainTabEnum.APPOINTMENT.toPageID()) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "Appointments"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setDisplayedTab(MainTabEnum.APPOINTMENT.toPath())

        } else if (tabNavigator.current is AccountTab && currentTabId == MainTabEnum.MORE.toPageID()) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "More"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setDisplayedTab(MainTabEnum.MORE.toPath())
        } else {
            imageTint = Color.White
            handleTint = Colors.darkPrimary
            imageStr = unselectedImage
        }



        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            modifier = Modifier.fillMaxHeight(),
            onClick = {
                tabNavigator.current = tab
            },
            selectedContentColor = Colors.primaryColor,
            unselectedContentColor = Colors.darkPrimary,

            icon = {
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Box(modifier = Modifier.fillMaxWidth(0.50f).fillMaxHeight(0.05f)
                        .background(color = handleTint, shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)), contentAlignment = Alignment.Center){}

                    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                        ImageComponent(
                            imageModifier = Modifier.size(imageSize.dp),
                            imageRes = imageStr,
                            colorFilter = ColorFilter.tint(imageTint)
                        )
                    }
                }
            }

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
