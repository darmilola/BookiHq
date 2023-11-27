package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import components.TextComponent
import screens.SplashScreenCompose

object MainScreen : Screen {
    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {
            Scaffold(
                content = {
                    CurrentTab()
                },
                backgroundColor = Color(0xFFFBFBF),
                bottomBar = {
                    BottomNavigation(modifier = Modifier
                        .padding(bottom = 16.dp)
                        .height(70.dp), backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                    {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(ConsultTab)
                        TabNavigationItem(ShopTab)
                        TabNavigationItem(DiaryTab)
                        TabNavigationItem(AccountTab)
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        selectedContentColor = Color(color = 0xFFFA2D65),
        unselectedContentColor = Color.Gray,
        label = {
            MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
                TextComponent(
                    text = tab.options.title,
                    fontSize = 15,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(fontFamily = GGSansSemiBold),
                    textColor = LocalContentColor.current,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    textModifier = Modifier.padding(top = 15.dp)
                )
            }

        },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title, modifier = Modifier.size(28.dp).padding(bottom = 5.dp)) } }
    )
}




