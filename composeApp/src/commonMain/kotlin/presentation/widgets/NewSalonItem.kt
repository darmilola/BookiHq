package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(start = 15.dp)
        .background(
            color = Color.White,
            shape = RoundedCornerShape(15.dp))
           .border(
            border = BorderStroke(1.5.dp, color = theme.Colors.lightGrayColor),
            shape = RoundedCornerShape(10.dp))
        ) {
        Card(
            modifier = Modifier.height(320.dp).fillMaxWidth()
                .clickable { onItemClickListener(vendor) },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
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
}



@Composable
fun NewSalonAddress(vendor: Vendor) {
    Column(
        modifier = Modifier
            .padding(top = 5.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            text = vendor.businessAddress!!,
            fontSize = 16,
            textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            textColor = theme.Colors.grayColor,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Normal,
            lineHeight = 20,
            textModifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        TextComponent(
            textModifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 10.dp),
            text = "@"+vendor.businessHandle,
            fontSize = 14,
            textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)

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
            textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 20,
            textModifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

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


