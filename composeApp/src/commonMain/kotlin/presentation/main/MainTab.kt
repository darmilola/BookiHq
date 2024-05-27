package presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import theme.styles.Colors
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.Models.HomepageInfo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import presentation.appointments.AppointmentsTab
import presentation.components.IndeterminateCircularProgressBar
import presentation.main.account.AccountTab
import presentation.main.home.HomeTab
import presentation.main.home.HomepageContract
import presentation.main.home.HomepagePresenter
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
import presentations.components.ImageComponent

class MainTab(private val mainViewModel: MainViewModel): Tab, KoinComponent {

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
           var isBottomNavSelected by remember { mutableStateOf(true) }
           TabNavigator(showDefaultTab(mainViewModel)) {
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
                            modifier = Modifier.height(40.dp), backgroundColor = Color.White,
                            elevation = 0.dp)
                        {
                            TabNavigationItem(
                                HomeTab(mainViewModel),
                                selectedImage = "drawable/home_icon.png",
                                unselectedImage = "drawable/home_outline.png",
                                imageSize = 24,
                                currentTabId = 0,
                                tabNavigator = it,
                                mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                            TabNavigationItem(
                                ShopTab(mainViewModel),
                                selectedImage = "drawable/shopping_basket.png",
                                unselectedImage = "drawable/shopping_basket_outline.png",
                                imageSize = 24,
                                currentTabId = 1,
                                tabNavigator = it,
                                mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                            TabNavigationItem(
                                ConsultTab(mainViewModel),
                                selectedImage = "drawable/video_chat.png",
                                unselectedImage = "drawable/video_chat_outline.png",
                                imageSize = 28,
                                currentTabId = 2,
                                tabNavigator = it,
                                mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                            TabNavigationItem(
                                AppointmentsTab(mainViewModel),
                                selectedImage = "drawable/appointment_icon.png",
                                unselectedImage = "drawable/appointment_outline.png",
                                imageSize = 28,
                                currentTabId = 3,
                                tabNavigator = it,
                                mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                            TabNavigationItem(
                                AccountTab(mainViewModel),
                                selectedImage = "drawable/user.png",
                                unselectedImage = "drawable/user_outline.png",
                                imageSize = 28,
                                currentTabId = 4,
                                tabNavigator = it,
                                mainViewModel
                            ) {
                                isBottomNavSelected = true
                            }
                        }
                    }
                )
            }
        }


    private fun showDefaultTab(mainViewModel: MainViewModel): HomeTab {

        return  HomeTab(mainViewModel)
    }
    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab, selectedImage: String, unselectedImage: String, imageSize: Int = 30, currentTabId: Int = 0, tabNavigator: TabNavigator, mainViewModel: MainViewModel, onBottomNavSelected:() -> Unit) {
        var imageStr by remember { mutableStateOf(unselectedImage) }
        var imageTint by remember { mutableStateOf(Color.Gray) }
        var screenTitle by remember { mutableStateOf("Home") }
        mainViewModel.setTitle(screenTitle)

        if(tabNavigator.current is ShopTab && currentTabId == 1){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            screenTitle = "Products"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else if(tabNavigator.current is ConsultTab && currentTabId == 2){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            screenTitle = "Meet With Therapist"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else if(tabNavigator.current is AppointmentsTab && currentTabId == 3){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            screenTitle = "Appointments"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else if(tabNavigator.current is AccountTab && currentTabId == 4){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            screenTitle = "Account"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else if (tabNavigator.current is HomeTab && currentTabId == 0){
            imageStr  = selectedImage
            imageTint = Colors.primaryColor
            screenTitle = "Home"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
        }

        else{
            imageTint = Color.DarkGray
            imageStr =   unselectedImage
            screenTitle = "Home"
        }

        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            onClick = {
                tabNavigator.current = tab
            },
            selectedContentColor = Colors.primaryColor,
            unselectedContentColor = Colors.darkPrimary,

            icon = {
                ImageComponent(imageModifier = Modifier.size(imageSize.dp), imageRes = imageStr, colorFilter = ColorFilter.tint(imageTint))
            }
        )
    }
}