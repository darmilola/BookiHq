package presentation.main

import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.viewmodels.MainViewModel
import presentation.widgets.SubtitleTextWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent

class NotificationTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Notifications"
            val icon = painterResource("drawable/notification.png")

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

        mainViewModel.setTitle(options.title)
        val columnModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxHeight()
            .fillMaxWidth()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {

                    StraightLine()

                    val listOfInt = ArrayList<Int>()
                    listOfInt.add(0)
                    listOfInt.add(2)
                    listOfInt.add(0)
                    listOfInt.add(0)
                    listOfInt.add(1)
                    listOfInt.add(2)
                    listOfInt.add(2)
                    listOfInt.add(0)

                    populateNotificationItemList(listOfInt)
                }

            }
    }

    @Composable
    private fun populateNotificationItemList(notificationItems: List<Int>){
        LazyColumn {
            items(notificationItems) {item ->
                GeneralNotificationItemCard(viewType = item)
            }
        }
    }



    @Composable
    fun GeneralNotificationItemCard(viewType: Int = 0) {
        var imageRes: String = ""

        when (viewType) {
            0 -> {
                imageRes = "drawable/schedule_icon.png"
            }
            1 -> {
                imageRes = "drawable/task_icon.png"
            }
            2 -> {
                imageRes = "drawable/schedule_icon.png"
            }
            3 -> {
                imageRes = "drawable/video_icon.png"
            }
            4 -> {
                imageRes = "drawable/purchase_icon.png"
            }
            else -> {
                imageRes = "drawable/schedule_icon.png"
            }
        }


        Card(
            modifier = Modifier
                .padding(end = 10.dp, top = 10.dp, bottom = 5.dp)
                .background(color = Color.White)
                .height(120.dp)
                .fillMaxWidth(),
            border = null
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment  = Alignment.Start,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.90f)
                        .background(color = Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth(0.25f)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.fillMaxSize(0.80f).background(color = Colors.lighterPrimaryColor).clip(RoundedCornerShape(15.dp)),contentAlignment = Alignment.Center){
                            ImageComponent(
                                imageModifier = Modifier.size(24.dp),
                                imageRes = imageRes,
                                colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {

                        AttachNotificationText(isUnseen = true)

                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .background(color = Color.White), contentAlignment = Alignment.BottomCenter
                ) {
                    StraightLine()
                }
            }

        }
    }




    @Composable
    fun AttachNotificationText(isUnseen: Boolean = false) {

           Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment  = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight().padding(end = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    TextComponent(
                        text = "Lorem ipsum dolor sit amet, consect adipiscing elit sit amet, consect adipiscing elit sit. consect adipiscing elit sit.",
                        fontSize = 16,
                        fontFamily = GGSansRegular,
                        textStyle = TextStyle(),
                        textColor = Color.DarkGray,
                        textAlign = TextAlign.Left,
                        maxLines = 3,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 23,
                        overflow = TextOverflow.Ellipsis)

                    TextComponent(
                        text = "Jan 2, 9:45 AM",
                        fontSize = 14,
                        fontFamily = GGSansRegular,
                        textStyle = TextStyle(),
                        textColor = Color.Gray,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 23,
                        overflow = TextOverflow.Ellipsis,
                        textModifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 10.dp)) }

                }

            }
        }
}


@Composable
fun AttachIcon(iconRes: String = "drawable/location_icon_filled.png", iconSize: Int = 16, iconTint: Color = Colors.primaryColor, onIconClicked:() -> Unit) {
    val modifier = Modifier
        .padding(top = 2.dp)
        .clickable {
            onIconClicked()
        }
        .size(iconSize.dp)
    ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = iconTint))
}


@Composable
fun StraightLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color(0x50CCCCCC))
    ) {
    }
}
