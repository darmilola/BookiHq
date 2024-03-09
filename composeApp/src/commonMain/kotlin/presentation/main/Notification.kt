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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                        .padding(bottom = 85.dp)
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
                imageRes = "schedule_icon.png"
            }
            1 -> {
                imageRes = "task_icon.png"
            }
            2 -> {
                imageRes = "schedule_icon.png"
            }
            3 -> {
                imageRes = "video_icon.png"
            }
            4 -> {
                imageRes = "purchase_icon.png"
            }
            else -> {
                imageRes = "schedule_icon.png"
            }
        }


        Card(
            modifier = Modifier
                .padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(color = Color.White)
                .height(100.dp)
                .fillMaxWidth(),
            border = null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.90f)
                    .background(color = Color.White)
            ) {
                    Row(modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth(0.20f).padding(bottom = 20.dp)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageComponent(imageModifier = Modifier.size(30.dp), imageRes = imageRes, colorFilter = ColorFilter.tint(color = Colors.primaryColor))
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
            Box (modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = Color.White), contentAlignment = Alignment.BottomCenter){
                StraightLine()
            }

        }
    }




    @Composable
    fun AttachNotificationText(isUnseen: Boolean = false) {
        val rowModifier = Modifier
            .padding(end = 10.dp)
            .fillMaxHeight()
            .fillMaxWidth()

        val indicatorModifier = Modifier
            .padding(top = 5.dp)
            .background(color = Color.Transparent)
            .size(9.dp)
            .background(
                color = Colors.pinkColor,
                shape = RoundedCornerShape(5.dp))


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment  = Alignment.CenterHorizontally,
                modifier = rowModifier
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.40f),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.80f)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        TextComponent(
                            text = "3h ago",
                            fontSize = 16,
                            fontFamily = GGSansRegular,
                            textStyle = TextStyle(),
                            textColor = Color.Gray,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Black,
                            lineHeight = 23,
                            overflow = TextOverflow.Ellipsis,
                            textModifier = Modifier.fillMaxWidth().wrapContentHeight()
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(modifier = indicatorModifier, contentAlignment = Alignment.TopEnd) {}

                        val expandedMenuItem = remember { mutableStateOf(false) }
                        AttachIcon(iconRes = "drawable/overflow_menu.png", iconSize = 25, iconTint = Color.Gray){
                            expandedMenuItem.value = true
                        }

                        val menuItems = arrayListOf<String>()
                        menuItems.add("Delete")

                        DropdownMenu(
                            expanded = expandedMenuItem.value,
                            onDismissRequest = { expandedMenuItem.value = false },
                            modifier = Modifier
                                .fillMaxWidth(0.40f)
                                .background(Color.White)
                        ) {
                            menuItems.forEachIndexed { index, title ->
                                DropdownMenuItem(
                                    onClick = {
                                    }) {
                                    SubtitleTextWidget(text = title, fontSize = 20)
                                }
                            }
                        }

                    }


                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    TextComponent(
                        text = "Lorem ipsum dolor sit amet, consect adipiscing elit.",
                        fontSize = 18,
                        fontFamily = GGSansRegular,
                        textStyle = TextStyle(),
                        textColor = Color.DarkGray,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Black,
                        lineHeight = 23,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
}


@Composable
fun AttachIcon(iconRes: String = "location_icon_filled.png", iconSize: Int = 16, iconTint: Color = Colors.primaryColor, onIconClicked:() -> Unit) {
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
