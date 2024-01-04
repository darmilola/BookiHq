package screens.UserProfile.ConnectBusiness

import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.GradientButton
import components.ImageComponent
import components.TextComponent
import widgets.InputWidget

@Composable
fun ConnectBusinessSearchBar(){

    InputWidget(iconRes = "drawable/search_icon.png", placeholderText = "Search...", iconSize = 24)

}


@Composable
fun ConnectBusinessHeader(){
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally) {
        ConnectBusinessTitle()
        ConnectBusinessDescription()
        ConnectBusinessSearchBar()
    }
}



@Composable
fun ConnectBusinessTitle(){
    val rowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val colModifier = Modifier
        .padding(top = 55.dp, end = 0.dp)
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
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem()
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                ConnectTitle()
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
fun BusinessInfoTitle(){
    val rowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val colModifier = Modifier
        .padding(top = 55.dp, end = 0.dp)
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
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem()
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                TextComponent(
                    text = "Details",
                    fontSize = 30,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                )
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
        text = "Search for your favorite business and connect with to get your services from them.",
        fontSize = 17,
        fontFamily = GGSansRegular,
        textStyle = TextStyle(),
        textColor = Color.Gray,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Black,
        lineHeight = 25,
        textModifier = Modifier.fillMaxWidth().wrapContentHeight().padding(start = 10.dp, end = 10.dp, top = 10.dp))
}




@Composable
fun leftTopBarItem() {
    val navigator = LocalNavigator.currentOrThrow
    val modifier = Modifier
        .padding(start = 15.dp, top = 3.dp)
        .clickable {
            navigator.pop()
        }
        .size(20.dp)
    ImageComponent(imageModifier = modifier, imageRes = "drawable/solid_back_icon.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
}

@Composable
fun ConnectTitle(){
    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
        TextComponent(
            text = "Connect",
            fontSize = 30,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
fun BusinessInfoContent(onSessionClickListener: () -> Unit) {
    val columnModifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
        .fillMaxWidth()
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxHeight(0.70f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BusinessLogo(size = 120, borderStroke = BorderStroke(2.dp, color = Color(color = 0xfffa2d65)))
                TextComponent(
                    text = "This is the Business name you are trying to connect with",
                    fontSize = 20,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 25,
                    textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, top = 10.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)

                AttachLocationIcon()

                TextComponent(
                    textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp), text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo ligula adipiscing Aene ligula", fontSize = 18, fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6, textColor = Color.Gray, textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold, lineHeight = 23, maxLines = 3,  overflow = TextOverflow.Ellipsis)

                AttachLocationInfoIcon()

                TextComponent(
                    textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp), text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo ligula adipiscing Aene ligula", fontSize = 18, fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6, textColor = Color.Gray, textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold, lineHeight = 23, maxLines = 3,  overflow = TextOverflow.Ellipsis)

                AttachActionButtons()
            }
        }
    }
}

@Composable
fun AttachActionButtons() {
    val gradientButtonStyle = Modifier
        .fillMaxWidth()
        .height(50.dp)

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        GradientButton(
            modifier = gradientButtonStyle,
            buttonText = "Connect",
            borderStroke = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            fontSize = 18,
            shape = CircleShape,
            textColor = Color.White,
            style = MaterialTheme.typography.h4,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    Color(color = 0xFFF43569),
                    Color(color = 0xFFFF823E)
                )
            )
        ) {
        }
    }

}


@Composable
fun AttachLocationIcon() {
    val modifier = Modifier
        .padding(top = 5.dp)
        .size(30.dp)
    ImageComponent(imageModifier = modifier, imageRes = "location_icon_filled.png", colorFilter = ColorFilter.tint(color = Color.LightGray))
}

@Composable
fun AttachLocationInfoIcon() {
    val modifier = Modifier
        .padding(top = 5.dp)
        .size(30.dp)
    ImageComponent(imageModifier = modifier, imageRes = "drawable/info_icon.png", colorFilter = ColorFilter.tint(color = Color.LightGray))
}


