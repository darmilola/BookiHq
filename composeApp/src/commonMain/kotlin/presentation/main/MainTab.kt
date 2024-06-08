package presentation.main

import androidx.compose.foundation.layout.Box
import theme.styles.Colors
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.PlatformNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import presentation.appointments.AppointmentsTab
import presentation.main.account.AccountTab
import presentation.main.home.HomeTab
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductResourceListEnvelopeViewModel
import presentation.viewmodels.ProductViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

class MainTab(private val mainViewModel: MainViewModel, private val platformNavigator: PlatformNavigator): Tab, KoinComponent {


    private var homePageViewModel: HomePageViewModel? = null
    private var productViewModel: ProductViewModel? = null
    private var productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel? = null
    private var appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel? = null
@OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Main"
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


        if (homePageViewModel == null) {
            homePageViewModel = kmpViewModel(
                factory = viewModelFactory {
                    HomePageViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (productViewModel == null) {
            productViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ProductViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (productResourceListEnvelopeViewModel == null) {
            productResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ProductResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }

        if (appointmentResourceListEnvelopeViewModel == null) {
            appointmentResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    AppointmentResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }

        var isBottomNavSelected by remember { mutableStateOf(true) }
            val userId = mainViewModel.userId.collectAsState()
            val vendorId = mainViewModel.vendorId.collectAsState()

            val bottomNavHeight =
                if (userId.value != -1 && vendorId.value != -1) 60 else 0

            TabNavigator(showDefaultTab(mainViewModel, homePageViewModel!!)) {
                Scaffold(
                    topBar = {
                        MainTopBar(
                            mainViewModel,
                            isBottomNavSelected = isBottomNavSelected
                        ) {
                            isBottomNavSelected = false
                        }
                    },
                    content = {
                        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                            CurrentTab()
                        }
                    },
                    backgroundColor = Color.White,
                    bottomBar = {
                        BottomNavigation(
                            modifier = Modifier.height(bottomNavHeight.dp),
                            backgroundColor = Color.White,
                            elevation = 0.dp
                        )
                        {
                            TabNavigationItem(
                                HomeTab(mainViewModel = mainViewModel, homePageViewModel = homePageViewModel!!),
                                selectedImage = "drawable/home_icon.png",
                                unselectedImage = "drawable/home_outline.png",
                                labelText = "Home",
                                imageSize = 22,
                                currentTabId = 0,
                                tabNavigator = it,
                                mainViewModel = mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                            TabNavigationItem(
                                ShopTab(mainViewModel,productViewModel!!, productResourceListEnvelopeViewModel!!),
                                selectedImage = "drawable/shopping_basket.png",
                                unselectedImage = "drawable/shopping_basket_outline.png",
                                labelText = "Shop",
                                imageSize = 22,
                                currentTabId = 1,
                                tabNavigator = it,
                                mainViewModel = mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                            TabNavigationItem(
                                AppointmentsTab(mainViewModel, appointmentResourceListEnvelopeViewModel!!, platformNavigator = platformNavigator),
                                selectedImage = "drawable/appointment_icon.png",
                                unselectedImage = "drawable/appointment_outline.png",
                                labelText = "History",
                                imageSize = 25,
                                currentTabId = 3,
                                tabNavigator = it,
                                mainViewModel = mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                            TabNavigationItem(
                                AccountTab(mainViewModel),
                                selectedImage = "drawable/more_icon_filled.png",
                                unselectedImage = "drawable/more_icon.png",
                                labelText = "More",
                                imageSize = 25,
                                currentTabId = 4,
                                tabNavigator = it,
                                mainViewModel = mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                        }
                    }
                )
            }
        }



    private fun showDefaultTab(mainViewModel: MainViewModel, homePageViewModel: HomePageViewModel): HomeTab {

        return  HomeTab(mainViewModel, homePageViewModel)
    }
    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab, selectedImage: String, unselectedImage: String, imageSize: Int = 30, labelText: String ,currentTabId: Int = 0, tabNavigator: TabNavigator, mainViewModel: MainViewModel, onBottomNavSelected:() -> Unit) {
        var imageStr by remember { mutableStateOf(unselectedImage) }
        var imageTint by remember { mutableStateOf(Colors.lightGray) }

        if(tabNavigator.current is ShopTab && currentTabId == 1){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            val screenTitle = "Products"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else if(tabNavigator.current is ConsultTab && currentTabId == 2){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            val screenTitle = "Meet With Therapist"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else if(tabNavigator.current is AppointmentsTab && currentTabId == 3){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            val screenTitle = "Appointments"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else if(tabNavigator.current is AccountTab && currentTabId == 4){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            val screenTitle = "Manage"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else if (tabNavigator.current is HomeTab && currentTabId == 0){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            val screenTitle = "Home"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else{
            imageTint = Colors.unSelectedBottomNav
            imageStr =   unselectedImage
        }

        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            modifier = Modifier.fillMaxHeight(),
            onClick = {
                tabNavigator.current = tab
            },
            selectedContentColor = Colors.primaryColor,
            unselectedContentColor = Colors.lightGray,

            icon = {
                Box(modifier = Modifier.height(30.dp), contentAlignment = Alignment.Center) {
                    ImageComponent(imageModifier = Modifier.size(imageSize.dp), imageRes = imageStr, colorFilter = ColorFilter.tint(imageTint))
                }
            },
            label = {
                Box(modifier = Modifier.height(30.dp), contentAlignment = Alignment.Center) {
                    TextComponent(
                        text = labelText,
                        fontSize = 15,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = imageTint,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 15
                    )
                }
            }

        )
    }
}