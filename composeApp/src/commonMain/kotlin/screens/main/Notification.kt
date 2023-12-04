package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppRegularTypography
import AppTheme.AppSemiBoldTypography
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.IconButtonComponent
import components.ImageComponent
import components.TextComponent
import screens.SplashScreenCompose

class NotificationTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Notifications"
            val icon = painterResource("notification.png")

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

        mainViewModel.setTitle(options.title.toString())
        val columnModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxHeight()
            .fillMaxWidth()

        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                ) {

                    GeneralNotificationItemCard()
                    TaskNotificationItemCard()
                    BookingNotificationItemCard()
                    ConsultNotificationItemCard()
                    OrdersNotificationItemCard()
                }

            }
        }
    }

    @Composable
      fun BookingNotificationItemCard() {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(color = Color.Transparent)
                .height(100.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            border = null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = Color(color = 0xFF68EA8C))
                            .fillMaxWidth(0.13f)
                            .fillMaxHeight()
                    ) {}
                    Row(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageComponent(imageModifier = Modifier.size(35.dp), imageRes = "schedule_icon.png", colorFilter = ColorFilter.tint(color = Color(color = 0xFF68EA8C)))
                    }
                }
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {

                      attachNotificationText(isUnseen = true)


                }
            }
        }
    }

    @Composable
    fun TaskNotificationItemCard() {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(color = Color.Transparent)
                .height(100.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            border = null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = Color(color = 0xFF65B8FA))
                            .fillMaxWidth(0.13f)
                            .fillMaxHeight()
                    ) {}
                    Row(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageComponent(imageModifier = Modifier.size(35.dp), imageRes = "task_icon.png", colorFilter = ColorFilter.tint(color = Color(color = 0xFF65B8FA)))
                    }
                }
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {

                    attachNotificationText()

                }
            }
        }
    }



    @Composable
    fun ConsultNotificationItemCard() {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(color = Color.Transparent)
                .height(100.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            border = null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = Color(color = 0xFFF8CF69))
                            .fillMaxWidth(0.13f)
                            .fillMaxHeight()
                    ) {}
                    Row(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageComponent(imageModifier = Modifier.size(35.dp), imageRes = "video_icon.png", colorFilter = ColorFilter.tint(color = Color(color = 0xFFF8CF69)))
                    }
                }
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {

                    attachNotificationText()

                }
            }
        }
    }


    @Composable
    fun OrdersNotificationItemCard() {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(color = Color.Transparent)
                .height(100.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            border = null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = Color(color = 0xFFDD6EEA))
                            .fillMaxWidth(0.13f)
                            .fillMaxHeight()
                    ) {}
                    Row(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageComponent(imageModifier = Modifier.size(35.dp), imageRes = "purchase_icon.png", colorFilter = ColorFilter.tint(color = Color(color = 0xFFDD6EEA)))
                    }
                }
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {

                    attachNotificationText(isUnseen = true)

                }
            }
        }
    }



    @Composable
    fun GeneralNotificationItemCard() {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(color = Color.Transparent)
                .height(100.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            border = null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = Color(color = 0xFFFF799D))
                            .fillMaxWidth(0.13f)
                            .fillMaxHeight()
                    ) {}
                    Row(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageComponent(imageModifier = Modifier.size(35.dp), imageRes = "app_logo_minimal.png", colorFilter = ColorFilter.tint(color = Color(color = 0xFFFF799D)))
                    }
                }
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {

                    attachNotificationText(isUnseen = true)

                }
            }
        }
    }




    @Composable
    fun attachNotificationText(isUnseen: Boolean = false) {
        val rowModifier = Modifier
            .padding(end = 10.dp)
            .fillMaxHeight()
            .fillMaxWidth()

        val textModifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

        val dateModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()

        val textColor: Color = if (isUnseen) Color.DarkGray else Color(0xFFB4B4B4)

        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment  = Alignment.CenterHorizontally,
                modifier = rowModifier
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.70f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextComponent(
                        text = "Lorem ipsum dolor sit amet, consect adipiscing elit.",
                        fontSize = 18,
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = textColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 23,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.Top
                ) {

                    TextComponent(
                        text = "07:45 AM",
                        fontSize = 14,
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = textColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }
        }
    }

}