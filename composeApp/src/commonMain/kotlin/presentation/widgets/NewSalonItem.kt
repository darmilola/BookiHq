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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.Vendor
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun NewVendorItem(vendor: Vendor, onItemClickListener: (Vendor) -> Unit) {

    val columnModifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
    Card(modifier = Modifier.height(360.dp).fillMaxWidth().clickable { onItemClickListener(vendor) },
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
                NewSalonImageImage(vendor.businessLogo!!)
                NewSalonName(vendor)
                NewSalonAddress(vendor)
            }
        }
    }
}



@Composable
fun NewSalonAddress(vendor: Vendor) {
    Column(
        modifier = Modifier
            .padding(top = 15.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            text = vendor.businessAddress!!,
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
            text = "@"+vendor.businessHandle,
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
fun NewSalonName(vendor: Vendor) {
    Column(
        modifier = Modifier
            .padding(top = 15.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            text = vendor.businessName!!,
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

        StraightLine()

    }

}

@Composable
fun NewSalonImageImage(imageUrl: String) {
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


