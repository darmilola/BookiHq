package screens.main

import Styles.Colors
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import components.ImageComponent
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import screens.Bookings.BookingScreen
import screens.Bookings.ServiceInformationPage
import screens.Products.CartScreen
import screens.UserProfile.SwitchVendor.ConnectPage
import screens.UserProfile.EditProfile
import screens.UserProfile.UserOrders.UserOrders
import screens.authentication.AuthenticationScreen
import screens.consultation.ConsultationScreen
import screens.consultation.VirtualConsultationRoom

object MainScreen : Screen {
    val mainViewModel = MainViewModel()

    @Composable
    override fun Content() {
        val screenId: State<Int> =  mainViewModel.screenId.observeAsState()
        val navigator = LocalNavigator.currentOrThrow
        var isBottomNavSelected by remember { mutableStateOf(true) }

        if (screenId.value == 1){
            navigator.push(BookingScreen(mainViewModel))
            mainViewModel.setId(-1)
        }
        if (screenId.value == 2){
            navigator.push(ConsultationScreen(mainViewModel))
            mainViewModel.setId(-1)
        }
        if (screenId.value == 3){
            navigator.push(CartScreen(mainViewModel))
            mainViewModel.setId(-1)
        }
        if (screenId.value == 4){
            navigator.replace(AuthenticationScreen(currentScreen = 4))
            mainViewModel.setId(-1)
        }
        if (screenId.value == 5){
            navigator.push(UserOrders(mainViewModel))
            mainViewModel.setId(-1)
        }
        if (screenId.value == 6){
            navigator.push(ConnectPage)
            mainViewModel.setId(-1)
        }
        if (screenId.value == 7){
            navigator.push(ServiceInformationPage)
            mainViewModel.setId(-1)
        }
        if (screenId.value == 8){
            navigator.push(VirtualConsultationRoom)
            mainViewModel.setId(-1)
        }
        if (screenId.value == 9){
            navigator.push(EditProfile(mainViewModel))
            mainViewModel.setId(-1)
        }

        TabNavigator(showDefaultTab(mainViewModel)) {
            Scaffold(
                topBar = {
                     MainTopBar(mainViewModel, isBottomNavSelected = isBottomNavSelected){
                         isBottomNavSelected = false
                     }
                },
                content = {
                     CurrentTab()
                },
                backgroundColor = Color.White,
                bottomBar = {
                    BottomNavigation(modifier = Modifier
                        .padding(bottom = 25.dp)
                        .height(50.dp), backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                    {
                        TabNavigationItem(HomeTab(mainViewModel), selectedImage = "drawable/home_icon.png", unselectedImage = "drawable/home_outline.png", imageSize = 28, currentTabId = 0, tabNavigator = it, mainViewModel){
                            isBottomNavSelected = true
                        }
                        TabNavigationItem(ShopTab(mainViewModel), selectedImage = "drawable/shopping_basket.png", unselectedImage = "drawable/shopping_basket_outline.png", imageSize = 28, currentTabId = 1, tabNavigator = it, mainViewModel){
                            isBottomNavSelected = true
                        }
                        TabNavigationItem(ConsultTab(mainViewModel), selectedImage = "drawable/video_chat.png", unselectedImage = "drawable/video_chat_outline.png", imageSize = 32, currentTabId = 2, tabNavigator = it, mainViewModel){
                            isBottomNavSelected = true
                        }
                        TabNavigationItem(BookingsTab(mainViewModel), selectedImage = "drawable/appointment_icon.png", unselectedImage = "drawable/appointment_outline.png", imageSize = 30, currentTabId = 3, tabNavigator = it, mainViewModel){
                            isBottomNavSelected = true
                        }
                        TabNavigationItem(AccountTab(mainViewModel), selectedImage = "drawable/user.png", unselectedImage = "drawable/user_outline.png", imageSize = 30, currentTabId = 4, tabNavigator = it, mainViewModel){
                            isBottomNavSelected = true
                        }
                    }
                }
            )
        }
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
        screenTitle = "Consultation"
        onBottomNavSelected()
        mainViewModel.setTitle(screenTitle)
    }

    else if(tabNavigator.current is BookingsTab && currentTabId == 3){
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





