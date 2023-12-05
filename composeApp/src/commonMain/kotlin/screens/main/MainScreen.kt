package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import components.TextComponent
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import screens.SplashScreenCompose
import screens.authentication.WelcomeScreenCompose
import screens.authentication.attachWaveIcon

object MainScreen : Screen {

    @Composable
    override fun Content() {

        val mainViewModel: MainViewModel = MainViewModel()
        var screenTitle: State<String> =  mainViewModel.screenTitle.observeAsState()

        TabNavigator(showDefaultTab(mainViewModel)) {
            Scaffold(
                topBar = {
                     MainTopBar(mainViewModel)
                },
                content = {
                     CurrentTab()
                },
                backgroundColor = Color(0xFFF3F3F3),
                bottomBar = {
                    BottomNavigation(modifier = Modifier
                        .padding(bottom = 30.dp)
                        .height(100.dp), backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                    {
                        TabNavigationItem(HomeTab(mainViewModel))
                        TabNavigationItem(ConsultTab(mainViewModel))
                        TabNavigationItem(ShopTab(mainViewModel))
                        TabNavigationItem(DiaryTab(mainViewModel))
                        TabNavigationItem(AccountTab(mainViewModel))
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
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = {
                    tabNavigator.current = tab
                  },
        selectedContentColor = Color(color = 0xFFFA2D65),
        unselectedContentColor = Color.DarkGray,
        label = {
            MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
                TextComponent(
                    text = tab.options.title,
                    fontSize = 14,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(fontFamily = GGSansSemiBold),
                    textColor = LocalContentColor.current,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    textModifier = Modifier.padding(top = 15.dp).wrapContentWidth()
                )
            }

        },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title, modifier = Modifier.size(28.dp).padding(bottom = 5.dp)) } }
    )
}

object MainScreenLanding : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        Row(modifier = Modifier.fillMaxSize()) {}
    }
}





