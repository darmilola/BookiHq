package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppRegularTypography
import GGSansBold
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.ButtonComponent
import components.ImageComponent
import components.TextComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.Bookings.BookingItemCard

class BookingsTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Bookings"
            val icon = painterResource("calender_icon_semi.png")



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
                        .background(color = Color(color = 0xFFF3F3F3))
                ) {
                    BookingToggleButton(borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), style = MaterialTheme.typography.h4)
                    val listOfInt = ArrayList<Pair<Int, Int>>()
                    listOfInt.add(Pair(0,1))
                    listOfInt.add(Pair(1,2))
                    listOfInt.add(Pair(2,3))

                    populateBookingItemList(listOfInt)
                }

            }
        }
    }


    @Composable
    fun BookingToggleButton(borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, fontSize: Int, style: TextStyle) {

        var isUpcomingChecked by remember { mutableStateOf(true) }

        val virtualTint  = if(isUpcomingChecked) Color(0xFFFA2D65) else Color.Transparent
        val inPersonTint  =  if(!isUpcomingChecked) Color(0xFFFA2D65) else Color.Transparent

        val virtualTextColor = if (isUpcomingChecked) Color.White else Color(0xFFFA2D65)

        val inPersonTextColor = if (isUpcomingChecked) Color(0xFFFA2D65) else Color.White



        val rowModifier = Modifier
            .padding(top = 15.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .background(color = Color(0x45C4C4C4), shape = RoundedCornerShape(10.dp))
        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {

                IconToggleButton(
                    checked = isUpcomingChecked,
                    onCheckedChange = { isUpcomingChecked = it }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.50f)
                            .height(50.dp)
                            .background(virtualTint, shape = shape),
                        contentAlignment = Alignment.Center,
                    ) {
                        TextComponent(
                            text = "Upcoming",
                            fontSize = fontSize,
                            textStyle = style,
                            fontFamily = GGSansSemiBold,
                            textColor = virtualTextColor,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                IconToggleButton(
                    checked = !isUpcomingChecked,
                    onCheckedChange = {
                        isUpcomingChecked = !it
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(inPersonTint, shape = shape),
                        contentAlignment = Alignment.Center,
                    ) {
                        TextComponent(
                            text = "Past",
                            fontSize = fontSize,
                            textStyle = style,
                            fontFamily = GGSansSemiBold,
                            textColor = inPersonTextColor,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

        }
    }



    @Composable
    private fun populateBookingItemList(bookingItems: List<Pair<Int,Int>>){
        LazyColumn {
            items(bookingItems) {item ->
                BookingItemCard(viewType = item.first, contentSize = ((item.second*135)), itemCount = item.second)
            }
        }
    }

    @Composable
    fun attachUserProfileImage() {
        Box(
            Modifier
                .padding(20.dp)
                .border(width = 3.dp, color = Color.DarkGray, shape = RoundedCornerShape(75.dp))
                .size(150.dp)
                .background(color = Color(0xFBFBFB))
        ) {
            val modifier = Modifier
                .padding(6.dp)
                .clip(CircleShape)
                .size(150.dp)
            ImageComponent(imageModifier = modifier, imageRes = "1.jpg")
        }

    }

    @Composable
    fun UserFullNameComp(){
        val rowModifier = Modifier
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {
                val modifier = Modifier.padding(start = 5.dp)
                TextComponent(
                    text = "Margaret C. Barbosa",
                    fontSize = 23,
                    fontFamily = GGSansBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30
                )
            }
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun EditProfileComp(style: TextStyle){

        val buttonStyle = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(0.40f)
            .background(color = Color.Transparent)
            .height(50.dp)

        ButtonComponent(modifier = buttonStyle, buttonText = "Edit Profile", borderStroke = BorderStroke((1.5).dp, color = Color(color = 0x90C8C8C8)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor =  Color.DarkGray, style = style){}

    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    public fun AccountActionButtonComponent(modifier: Modifier, buttonText: String, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, iconRes: String, onClick: (() -> Unit)? = null) {
        val rowModifier = Modifier
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppRegularTypography()) {


            Button(
                onClick = {
                    if (onClick != null) {
                        onClick()
                    }
                },
                modifier = modifier,
                colors = colors,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = rowModifier
                ) {

                    val iconModifier = Modifier
                        .padding(top = 5.dp)
                        .size(24.dp)

                    val iconTextBoxModifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.90f)

                    val iconNavModifier = Modifier
                        .fillMaxWidth()
                        .size(28.dp)

                    val textModifier = Modifier
                        .padding(top = 2.dp, end = 5.dp, start = 20.dp)
                        .wrapContentSize()
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                        modifier = iconTextBoxModifier
                    ) {
                        ImageComponent(imageModifier = iconModifier, imageRes = iconRes)
                        TextComponent(
                            text = buttonText,
                            fontSize = fontSize,
                            textStyle = style,
                            textColor = textColor,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = GGSansSemiBold,
                            textModifier = textModifier
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top,
                        modifier = iconNavModifier
                    ) {
                        ImageComponent(imageModifier = iconModifier, imageRes = "chevron_right.png")
                    }

                }
            }
        }
    }


    @Composable
    fun attachAccountAction() {
        val columnModifier = Modifier
            .padding(top = 20.dp, start = 5.dp)
            .fillMaxWidth()

        val buttonStyle = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth()
            .height(50.dp)

        MaterialTheme(colors = AppColors(), typography = AppRegularTypography()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = columnModifier
            ) {

                AccountActionButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Settings",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color.DarkGray,
                    style = MaterialTheme.typography.button,
                    iconRes = "settings_icon.png"
                ) {}


                AccountActionButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "My Bookings",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color.DarkGray,
                    style = MaterialTheme.typography.button,
                    iconRes = "booking_icon.png"
                ) {}

                AccountActionButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Address",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color.DarkGray,
                    style = MaterialTheme.typography.button,
                    iconRes = "location_icon.png"
                ) {}

                Divider(color = Color(color = 0x90C8C8C8), thickness = 2.dp, modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 20.dp))


                AccountActionButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Help & Support",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color.DarkGray,
                    style = MaterialTheme.typography.button,
                    iconRes = "help_circle_icon.png"
                ) {}

                AccountActionButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Log out",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color.DarkGray,
                    style = MaterialTheme.typography.button,
                    iconRes = "logout_icon.png"
                ) {}

            }
        }
    }

}