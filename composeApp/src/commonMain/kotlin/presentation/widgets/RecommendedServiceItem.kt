package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import domain.Enums.Currency
import domain.Models.VendorRecommendation
import domain.Enums.RecommendationType
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun RecommendedServiceItem(vendorRecommendation: VendorRecommendation,mainViewModel: MainViewModel, onItemClickListener: (VendorRecommendation) -> Unit) {


    val columnModifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
    Card(modifier = Modifier.height(360.dp).fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp),
        border = BorderStroke(0.5.dp, color = Colors.lighterPrimaryColor),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Colors.primaryColor)
                        .fillMaxWidth(0.01f)
                        .fillMaxHeight()
                )
                Column(
                    modifier = columnModifier
                ) {
                    RecommendedServicesImage("https://cdn.pixabay.com/photo/2017/06/05/07/58/butterfly-2373175_1280.png")
                    RecommendedServiceDescription(vendorRecommendation)
                    RecommendedServicePriceAndAction(vendorRecommendation,mainViewModel, onItemClickListener = {
                        onItemClickListener(it)
                    })
                }
            }
    }
}


@Composable
fun RecommendedServicePriceAndAction(vendorRecommendation: VendorRecommendation, mainViewModel: MainViewModel, onItemClickListener: (VendorRecommendation) -> Unit) {
    val currencyUnit = mainViewModel.displayCurrencyUnit.value
    Row(modifier = Modifier.height(50.dp).padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {

        Row(modifier = Modifier.fillMaxWidth(0.4f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            if (vendorRecommendation.recommendationType == RecommendationType.Services.toPath()){
                PopularServicePriceContent(vendorRecommendation.serviceTypeItem?.price.toString(), currencyUnit)
            }
            else{
                PopularServicePriceContent(vendorRecommendation.product?.productPrice.toString(), currencyUnit)
            }

        }

        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize().clickable {
                    onItemClickListener(vendorRecommendation)
                }
            ) {
                TextComponent(
                    text = if(vendorRecommendation.recommendationType == RecommendationType.Services.toPath()) "Book Now" else "Buy Now",
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.primaryColor,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 20,
                    maxLines = 2,
                    fontSize = 16,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = Modifier.wrapContentSize().padding(end = 7.dp))

                    ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/forward_arrow.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
            }

        }
    }

}
@Composable
fun PopularServicePriceContent(price: String, currencyUnit: String) {
     TextComponent(
            text = "$currencyUnit$price",
            fontSize = 16,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.primaryColor,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textModifier = Modifier
                .padding(top = 5.dp)
                .wrapContentSize()
        )
    }

@Composable
fun RecommendedServiceDescription(vendorRecommendation: VendorRecommendation) {
    val recommendationType = vendorRecommendation.recommendationType
    Column(
        modifier = Modifier
            .padding(top = 15.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            text = if (recommendationType == RecommendationType.Services.toPath())
                vendorRecommendation.serviceTypeItem?.title!! else vendorRecommendation.product?.productName!!,
            fontSize = 18,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            textModifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        TextComponent(
            textModifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 10.dp),
            text = vendorRecommendation.description,
            fontSize = 14, fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)

         StraightLine()

    }

}

@Composable
fun RecommendedServicesImage(imageUrl: String) {
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
                imageRes = imageUrl,
                isAsync = true,
                contentScale = ContentScale.Crop
            )
        }
}
@Composable
fun StraightLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color(color = 0x80CCCCCC))
    ) {}
}


