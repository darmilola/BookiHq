package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.BorderStroke
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
        .padding(top = 10.dp)
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
    SubtitleTextWidget(text = title, textColor = Colors.primaryColor, fontSize = 20)
}


@Composable
fun BusinessInfoContent(vendor: Vendor) {

    val isMobileServiceAvailable = vendor?.isMobileServiceAvailable

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.5.dp
        )
    ) {

        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    border = BorderStroke(1.5.dp, color = theme.Colors.lightGrayColor),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 15.dp, end = 10.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {

                businessInfo(title = "Business Name", info = vendor?.businessName!!)
                businessInfo(title = "Location", info = vendor.businessAddress!!)
                businessInfo(title = "City", info = vendor.city!!)
                businessInfo(title = "Handle", info = vendor.businessHandle!!)
                businessInfo(title = "About Business", info = vendor.businessAbout!!)
                businessInfo(title = "Opening Time", info = vendor.openingTime!!)
                businessInfo(title = "Contact Phone", info = vendor.contactPhone!!)
                businessInfo(title = "Home Services", info = if (isMobileServiceAvailable!!) "Available" else "No Home Service")
            }
        }
    }
}

@Composable
fun businessInfo(title: String, info: String){
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 20.dp)){

        TextComponent(
            textModifier = Modifier.wrapContentWidth()
                .padding(bottom = 5.dp),
            text = title,
            fontSize = 16,
            textStyle = MaterialTheme.typography.titleMedium,
            textColor = Color(0xFFb0afaf),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 23,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        TextComponent(
            textModifier = Modifier.wrapContentWidth(),
            text = info,
            fontSize = 16,
            textStyle = MaterialTheme.typography.titleMedium,
            textColor = theme.Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 23,
            maxLines = 20,
            overflow = TextOverflow.Ellipsis
        )

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


