package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Enums.ServiceLocationEnum
import domain.Models.Product
import domain.Models.VendorPackage
import presentation.Packages.Packages
import presentation.components.ButtonComponent
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun PackageItem(vendorPackage: VendorPackage, onPackageClickListener: (VendorPackage) -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .clickable {
            onPackageClickListener(vendorPackage)
        }
        .height(200.dp)
    Row(modifier = columnModifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        PackageImage(vendorPackage)
        PackageDescription(vendorPackage, onPackageClickListener = {
            onPackageClickListener(it)
        })
    }
}

@Composable
fun PackageImage(vendorPackage: VendorPackage) {
    val imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp)
            .background(color = Color.White)
            .height(200.dp)
            .fillMaxWidth(0.45f),
        shape = RoundedCornerShape(8.dp),
        border = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.TopStart
        ) {
            if (vendorPackage.packageImages.isNotEmpty()) {
                ImageComponent(
                    imageModifier = imageModifier,
                    imageRes = vendorPackage.packageImages[0].imageUrl,
                    contentScale = ContentScale.Crop,
                    isAsync = true
                )
            }
        }
    }
}


@Composable
fun PackageDescription(vendorPackage: VendorPackage, onPackageClickListener: (VendorPackage) -> Unit){
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            text = vendorPackage.title,
            fontSize = 16,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2)
        PackageDescriptionText(vendorPackage)
        PackageInfo(vendorPackage)
        ViewPackage(vendorPackage, onPackageClickListener = {
            onPackageClickListener(it)
        })
    }
}

@Composable
fun ViewPackage(vendorPackage: VendorPackage, onPackageClickListener: (VendorPackage) -> Unit){
    val buttonStyle = Modifier
        .padding(top = 10.dp)
        .fillMaxWidth()
        .background(color = Color.Transparent)
        .height(40.dp)
    ButtonComponent(modifier = buttonStyle, buttonText = "View Package", borderStroke = BorderStroke((1).dp, color = Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 14, shape = RoundedCornerShape(12.dp), textColor =  Colors.primaryColor, style = TextStyle(fontFamily = GGSansRegular)){
        onPackageClickListener(vendorPackage)
    }
}

@Composable
fun PackageInfo(vendorPackage: VendorPackage){
    val packageServices = vendorPackage.packageServices.size
    val products = vendorPackage.packageProducts.size
    val price = vendorPackage.price
    val rowModifier = Modifier
        .fillMaxWidth().height(25.dp)
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = rowModifier
    ) {
        Row (
            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TextComponent(
                text = "$packageServices Services",
                textModifier = Modifier.wrapContentSize()
                    .padding(start = 5.dp),
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.serviceLightGray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
            )

            Box(
                modifier = Modifier.width(25.dp).fillMaxHeight()
                    .padding(start = 10.dp, end = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(6.dp).clip(CircleShape)
                        .background(color = Colors.serviceLightGray)
                ) {}
            }
        }

        Row (
            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TextComponent(
                text = "$products Products",
                textModifier = Modifier.wrapContentSize()
                    .padding(start = 5.dp),
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.serviceLightGray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
            )

            Box(
                modifier = Modifier.width(25.dp).fillMaxHeight()
                    .padding(start = 10.dp, end = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(6.dp).clip(CircleShape)
                        .background(color = Colors.serviceLightGray)
                ) {}
            }
        }

        Row (
            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TextComponent(
                text = "$$price",
                textModifier = Modifier.wrapContentSize()
                    .padding(start = 5.dp),
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.serviceLightGray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
            )
        }

       }

    }



@Composable
fun PackageDescriptionText(vendorPackage: VendorPackage) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        val modifier = Modifier
            .fillMaxWidth()
        TextComponent(
            text = vendorPackage.description,
            fontSize = 15,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Medium,
            lineHeight = 20,
            textModifier = modifier,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis)

    }

}