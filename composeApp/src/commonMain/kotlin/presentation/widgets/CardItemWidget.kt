package presentation.widgets

import GGSansRegular
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Enums.CardType
import domain.Models.PaymentCard
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors
import utils.getCardType

@Composable
fun PaymentCardItem(paymentCard: PaymentCard, onPaymentCardSelected: (PaymentCard) -> Unit, onPaymentCardRemoved: (PaymentCard) -> Unit) {
    val selectedBgColor: Color = if (paymentCard.isSelected) Colors.lightPrimaryColor else Color.White
    val rowModifier = Modifier
        .background(color = selectedBgColor, shape = RoundedCornerShape(15.dp))
        .padding(top = 10.dp, bottom = 10.dp)
        .clickable {
            onPaymentCardSelected(paymentCard)
        }
        .height(100.dp).fillMaxWidth()
    Row(modifier = rowModifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardTypeImage(paymentCard)
        CardDescription(paymentCard, onPaymentCardRemoved)
    }
}

@Composable
fun CardTypeImage(paymentCard: PaymentCard) {
    val firstDigit = paymentCard.cardNumber[0]
    val cardType = getCardType(firstDigit.digitToInt())
    var backgroundColor: Color = Color.Black
    var imageRes = "drawable/visa_icon.png"

    when (cardType) {
        CardType.MASTERCARD.toPath() -> {
            backgroundColor = Color.Black
            imageRes = "drawable/mastercard_icon.png"
        }
        CardType.VISA.toPath() -> {
            backgroundColor = Color(color = 0xff0253a5)
            imageRes = "drawable/visa_icon.png"
        }
        CardType.AMEX.toPath() -> {
            backgroundColor = Color(color = 0xff3498d8)
            imageRes = "drawable/amex_icon.png"
        }
        else -> {
            backgroundColor = Color(color = 0xffd72927)
            imageRes = "drawable/card_error_icon.png"
        }
    }

    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp)
            .background(color = Color.Transparent)
            .height(60.dp)
            .width(80.dp),
        shape = RoundedCornerShape(10.dp),
        border = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundColor)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .padding(10.dp)
                .size(50.dp)
            ImageComponent(imageModifier = modifier, imageRes = imageRes)
        }
    }
}


@Composable
fun CardDescription(paymentCard: PaymentCard, onPaymentCardRemoved: (PaymentCard) -> Unit){
    val cardNumber = paymentCard.cardNumber
    val lastFourDigit = cardNumber.substring(IntRange(cardNumber.length-4,cardNumber.length-1))
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxWidth(0.80f)
        .fillMaxHeight()
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                fourBullets()
                fourBullets()
                fourBullets()
                TextComponent(
                    text = lastFourDigit,
                    fontSize = 20,
                    textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }

            TextComponent(
                text = paymentCard.expiryMonth + "/" + paymentCard.expiryYear,
                fontSize = 16,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 20,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )

        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val modifier = Modifier
                .clickable {
                    onPaymentCardRemoved(paymentCard)
                }
                .size(18.dp)
            ImageComponent(
                imageModifier = modifier,
                imageRes = "drawable/cancel_icon_filled.png",
                colorFilter = ColorFilter.tint(color = Colors.pinkColor)
            )
        }
    }
}

@Composable
fun fourBullets(){
    TextComponent(
        text = "\u2022",
        fontSize = 24,
        fontFamily = GGSansRegular,
        textStyle = MaterialTheme.typography.h6,
        textColor = Colors.darkPrimary,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 20,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2)
    TextComponent(
        text = "\u2022",
        fontSize = 24,
        fontFamily = GGSansRegular,
        textStyle = MaterialTheme.typography.h6,
        textColor = Colors.darkPrimary,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 20,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2)
    TextComponent(
        text = "\u2022",
        fontSize = 24,
        fontFamily = GGSansRegular,
        textStyle = MaterialTheme.typography.h6,
        textColor = Colors.darkPrimary,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 20,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2)
    TextComponent(
        text = "\u2022",
        fontSize = 24,
        fontFamily = GGSansRegular,
        textStyle = MaterialTheme.typography.h6,
        textColor = Colors.darkPrimary,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 20,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2)
    TextComponent(
        text = " ",
        fontSize = 24,
        fontFamily = GGSansRegular,
        textStyle = MaterialTheme.typography.h6,
        textColor = Colors.darkPrimary,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 20,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2)
}

