package presentation.widgets

import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.Vendor
import presentation.components.ButtonComponent
import presentation.connectVendor.BusinessLogo
import presentations.components.ImageComponent
import presentations.components.TextComponent


@Composable
fun SwitchVendorHeader(title: String){
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally) {
        ConnectBusinessTitle(title)
    }
}



@Composable
fun ConnectBusinessTitle(title: String){
    val rowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val colModifier = Modifier
        .padding(top = 20.dp, end = 0.dp)
        .fillMaxWidth()
        .height(40.dp)

    Column(modifier = colModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {}

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                ConnectTitle(title)
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
            }
        }
    }
}


@Composable
fun ConnectBusinessDescription(){
    TextComponent(
        text = "Search for your favorite vendor and connect with to get your services from them.",
        fontSize = 16,
        fontFamily = GGSansRegular,
        textStyle = TextStyle(),
        textColor = Color.DarkGray,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Normal,
        lineHeight = 20,
        textModifier = Modifier.fillMaxWidth().wrapContentHeight().padding(start = 10.dp, end = 10.dp, top = 20.dp))
}




@Composable
fun leftTopBarItem(onBackPressed: () -> Unit) {
    PageBackNavWidget {
        onBackPressed()
    }

}

@Composable
fun ConnectTitle(title: String){
    TitleWidget(title = title, textColor = Colors.primaryColor)
}


@Composable
fun BusinessInfoContent(vendor: Vendor, isViewOnly: Boolean = false, onConnectedListener: () -> Unit) {
    val columnModifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
        .fillMaxWidth()
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxHeight(0.90f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BusinessLogo(logoUrl = vendor.businessLogo!!)
                TextComponent(
                    text = vendor.businessName!!,
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 25,
                    textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, top = 10.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                AttachLocationIcon()

                TextComponent(
                    textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, top = 5.dp),
                    text = vendor.businessAddress!!,
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(),
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 25,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                AttachLocationInfoIcon()

                TextComponent(
                    textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, top = 5.dp),
                    text = vendor.businessAbout!!,
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(),
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 25,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                        .padding(start = 30.dp, end = 30.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.fillMaxHeight().wrapContentWidth(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            val modifier = Modifier
                                .size(24.dp)
                            ImageComponent(
                                imageModifier = modifier,
                                imageRes = "drawable/open_sign.png",
                                colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxHeight().wrapContentWidth(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            TextComponent(
                                textModifier = Modifier.wrapContentWidth()
                                    .padding(start = 5.dp),
                                text = vendor.openingTime.toString(),
                                fontSize = 16,
                                textStyle = TextStyle(),
                                textColor = Color.DarkGray,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 23,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier.fillMaxHeight().wrapContentWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val modifier = Modifier
                            .size(24.dp)
                        ImageComponent(
                            imageModifier = modifier,
                            imageRes = "drawable/mobile_service.png",
                            colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxHeight().wrapContentWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        TextComponent(
                            textModifier = Modifier.wrapContentWidth()
                                .padding(start = 5.dp),
                            text = if (vendor.isMobileServiceAvailable) "Home Service Available" else "No Home Service",
                            fontSize = 16,
                            textStyle = TextStyle(),
                            textColor = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 23,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (!isViewOnly){

                    val buttonStyle = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp)
                        .height(45.dp)

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ButtonComponent(
                            modifier = buttonStyle,
                            buttonText = "Connect",
                            borderStroke = null,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                            fontSize = 16,
                            shape = CircleShape,
                            textColor = Color.White,
                            style = TextStyle()
                        ) {
                            onConnectedListener()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AttachLocationIcon() {
    val modifier = Modifier
        .padding(top = 5.dp)
        .size(30.dp)
    ImageComponent(imageModifier = modifier, imageRes = "drawable/location_icon_filled.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
}

@Composable
fun AttachLocationInfoIcon() {
    val modifier = Modifier
        .padding(top = 5.dp)
        .size(30.dp)
    ImageComponent(imageModifier = modifier, imageRes = "drawable/info_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
}


