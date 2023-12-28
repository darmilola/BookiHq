package widgets

import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansRegular
import GGSansSemiBold
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import screens.Products.NewProductDescriptionText
import screens.Products.ViewNewProduct

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendedServiceItem(onSessionClickListener: () -> Unit) {
    val columnModifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        .clickable {
            onSessionClickListener()
        }
        .height(410.dp)
        .fillMaxWidth()
    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                .height(410.dp)
                .wrapContentWidth()
        ) {
            Column(
                modifier = columnModifier
            ) {
                RecommendedServicesImage()
                RecommendedServiceDescription()
                RecommendedServicePriceAndAction()
            }
        }
    }
}

@Composable
fun RecommendedServicePriceAndAction() {
    Row(modifier = Modifier.height(50.dp).padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {

        Row(modifier = Modifier.fillMaxWidth(0.4f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            PopularServicePriceContent()
        }

        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                TextComponent(
                    text = "Book Now",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color(color = 0xfffa2d65),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    textModifier = Modifier.wrapContentSize().padding(end = 7.dp))

                    ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/forward_arrow.png", colorFilter = ColorFilter.tint(color = Color(color = 0xfffa2d65)))
            }

        }
    }

}


@Composable
fun PopularServiceFav() {
    Row {
        ImageComponent(imageModifier = Modifier.size(24.dp).padding(top = 2.dp), imageRes = "fav_icon.png", colorFilter = ColorFilter.tint(color = Color(0xfffa2d65)))
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
fun PopularServicePriceContent() {
     TextComponent(
            text = "$670,000",
            fontSize = 20,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color(color = 0xfffa2d65),
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .padding(top = 5.dp)
                .wrapContentSize()
        )
    }

@Composable
fun RecommendedServiceDescription() {
    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        val modifier = Modifier
            .fillMaxWidth()

        TextComponent(
            text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean",
            fontSize = 20,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 25,
            textModifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        TextComponent(
            textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp), text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo ligula adipiscing Aene ligula", fontSize = 18, fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6, textColor = Color.Gray, textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold, lineHeight = 23, maxLines = 3,  overflow = TextOverflow.Ellipsis)

        StraightLine()

    }

}

@Composable
fun RecommendedServicesImage() {
    val imageModifier =
        Modifier
            .height(200.dp)
            .fillMaxWidth()
        Box(
            modifier = imageModifier,
            contentAlignment = Alignment.TopStart
        ) {
            ImageComponent(
                imageModifier = imageModifier,
                imageRes = "popular.jpg",
                contentScale = ContentScale.Crop
            )
        }
}


@Composable
fun PopularServiceDescription(onProductClickListener: () -> Unit){
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment  = Alignment.Start,
        ) {

            val modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()

            TextComponent(
                text = "Bloom Rose Oil And Argan Oil is For Sale",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 25,
                textModifier = modifier
            )
            NewProductDescriptionText()
            ViewNewProduct(onProductClickListener)
        }
    }
}

@Composable
fun StraightLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = Color(color = 0x80CCCCCC))
    ) {
    }
}


