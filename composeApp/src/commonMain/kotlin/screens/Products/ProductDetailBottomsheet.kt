package screens.Products

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import components.ButtonComponent
import components.ImageComponent
import components.TextComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import widgets.CartIncrementDecrementWidget
import widgets.IncrementDecrementWidget

@Composable
fun ProductDetailContent() {
    Scaffold(
        content = {
            SheetContent()
        },
        backgroundColor = Color(0xFFF3F3F3),
        bottomBar = {
            BottomNavigation(modifier = Modifier
                .padding(top = 10.dp)
                .height(80.dp), backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
            {

                val buttonStyle2 = Modifier
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 4.dp)
                    .fillMaxWidth()
                    .height(50.dp)

                val bgStyle = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()


                Row (modifier = bgStyle,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Row(modifier = Modifier.fillMaxWidth(0.50f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        CartIncrementDecrementWidget()
                    }
                    ButtonComponent(modifier = buttonStyle2, buttonText = "Add to Cart", colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFF43569)), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4, borderStroke = null){}
                }

            }
        }

    )
}


@Composable
fun SheetContent() {

    var currentTabScreen by remember { mutableStateOf(0) }

    val boxModifier =
        Modifier
            .height(350.dp)
            .fillMaxWidth()
    Column(
        Modifier
            .padding(bottom = 80.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {
        Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
            attachServiceImages()
            SheetContentHeader()
        }
        ProductNameInfoContent()
        Divider(color = Color(color = 0x90C8C8C8), thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.90f).padding(top = 20.dp))

        ToggleButton(borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), style = MaterialTheme.typography.h4, onDescriptionClicked = {
          currentTabScreen = 0
        }, onReviewsClicked = {
            currentTabScreen = 1
        })
        ProductTabScreenCompose(currentTabScreen)
    }
}



@Composable
fun ProductDescription() {

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Description",
            fontSize = 23,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )

   TextComponent(
            textModifier = Modifier.fillMaxWidth().padding(top = 10.dp), text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula\n \n adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula  ", fontSize = 18, fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6, textColor = Color(color = 0xFF5F5E5E), textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold, lineHeight = 25)
    }

}


@Composable
fun ProductNameInfoContent() {
    Row(modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            .fillMaxWidth(0.7f)) {
            ProductFavInfoContent()
            ProductTitle()
        }
        ProductPriceInfoContent()
    }
}

@Composable
fun ProductPriceInfoContent() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
    ) {
        TextComponent(
            text = "$67,000",
            fontSize = 18,
            fontFamily = GGSansBold,
            textStyle = TextStyle(textDecoration = TextDecoration.LineThrough),
            textColor = Color.LightGray,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Medium,
            lineHeight = 30,
            textModifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )

        TextComponent(
            text = "$670,000",
            fontSize = 23,
            fontFamily = GGSansBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color(color = 0xfffa2d65),
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }



}

@Composable
fun ProductFavInfoContent() {
   Row {
       ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "fav_icon.png", colorFilter = ColorFilter.tint(color = Color(0xfffa2d65)))
       TextComponent(
           text = "500",
           fontSize = 20,
           fontFamily = GGSansSemiBold,
           textStyle = MaterialTheme.typography.h6,
           textColor = Color.DarkGray,
           textAlign = TextAlign.Left,
           fontWeight = FontWeight.SemiBold,
           lineHeight = 30,
           textModifier = Modifier
               .padding(start = 5.dp)
               .fillMaxWidth()
               .wrapContentHeight()
       )
   }
}

@Composable
fun SheetContentHeader() {
    Row(modifier = Modifier.height(80.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.CenterStart) {
            attachCancelIcon()
        }
        Box(Modifier.weight(3f)) {

        }
        Box(modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.CenterEnd) {
            attachShareIcon()
        }
    }
}

@Composable
fun ProductTitle(){
    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Bloom Rose Oil And Argan Oil",
            fontSize = 23,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun attachServiceImages(){

    val pagerState = rememberPagerState(pageCount = {
        3
    })

    val  boxModifier =
        Modifier
            .padding(bottom = 20.dp)
            .fillMaxHeight()
            .fillMaxWidth()

    // AnimationEffect
    Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ImageComponent(imageModifier = Modifier.fillMaxWidth().height(350.dp), imageRes = "$page.jpg", contentScale = ContentScale.Crop)
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                var color = Color.White
                var width = 0
                if (pagerState.currentPage == iteration){
                    color =  Color(color = 0xFFF43569)
                    width = 20
                } else{
                    color =  Color(0xFFF3F3F3)
                    width = 20
                }
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(5.dp)
                        .width(width.dp)
                )
            }

        }
    }

}

@Composable
fun attachCancelIcon() {
    Box(
        Modifier
            .clip(CircleShape)
            .size(45.dp)
            .background(color = Color(color = 0xA9E8E8E8)),
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .size(25.dp)
        ImageComponent(imageModifier = modifier, imageRes = "cancel_icon.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
    }

}

@Composable
fun attachShareIcon() {
    Box(
        Modifier
            .clip(CircleShape)
            .size(45.dp)
            .background(color = Color(color = 0xA9E8E8E8)),
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .size(25.dp)
        ImageComponent(imageModifier = modifier, imageRes = "export_icon.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
    }

}



@Composable
fun ToggleButton(borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, fontSize: Int, style: TextStyle, onDescriptionClicked: () ->  Unit, onReviewsClicked: () ->  Unit) {

    var isDescriptionChecked by remember { mutableStateOf(true) }

    val virtualTint  = if(isDescriptionChecked) Color(0xffFA2D65) else Color.Transparent
    val inPersonTint  =  if(!isDescriptionChecked) Color(0xffFA2D65) else Color.Transparent

    val virtualTextColor = if (isDescriptionChecked) Color.White else Color(0xffFA2D65)

    val inPersonTextColor = if (isDescriptionChecked) Color(0xffFA2D65) else Color.White



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
                checked = isDescriptionChecked,
                onCheckedChange = {
                    onDescriptionClicked()
                    isDescriptionChecked = it
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.50f)
                        .height(50.dp)
                        .background(virtualTint, shape = shape),
                    contentAlignment = Alignment.Center,
                ) {
                    TextComponent(
                        text = "Description",
                        fontSize = fontSize,
                        textStyle = style,
                        fontFamily = GGSansSemiBold,
                        textColor = virtualTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            IconToggleButton(
                checked = !isDescriptionChecked,
                onCheckedChange = {
                    onReviewsClicked()
                    isDescriptionChecked = !it
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
                        text = "Reviews(20)",
                        fontSize = fontSize,
                        textStyle = style,
                        fontFamily = GGSansSemiBold,
                        textColor = inPersonTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}
