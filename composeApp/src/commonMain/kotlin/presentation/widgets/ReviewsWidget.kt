package presentation.widgets

import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.ProductReview
import domain.Models.Reviewer
import domain.Models.TherapistReviews
import domain.Models.User
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun TherapistReviewsWidget(reviews: TherapistReviews) {

    val columnModifier = Modifier
        .padding(start = 5.dp, bottom = 10.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        AttachSpecialistReviewHeader(reviews)
        AttachUserReviewsContent(reviews.reviewText!!)
    }

}


@Composable
fun ProductReviewsWidget(reviews: ProductReview) {
    val columnModifier = Modifier
        .padding(start = 5.dp, bottom = 10.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        AttachProductReviewHeader(reviews)
        AttachUserReviewsContent(reviews.reviewText!!)
    }

}


@Composable
fun AttachUserImage(profileImageUrl: String) {
    Box(
        Modifier
            .border(width = 1.dp, color = Colors.primaryColor, shape = CircleShape)
            .size(50.dp)
    ) {
        val modifier = Modifier
            .padding(1.dp)
            .clip(shape = RoundedCornerShape(35.dp))
            .size(50.dp)
        ImageComponent(imageModifier = modifier, imageRes = profileImageUrl, isAsync = true)
    }

}

@Composable
fun AttachSpecialistReviewHeader(reviews: TherapistReviews) {
    val rowModifier = Modifier
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            AttachUserImage(reviews.reviewer?.profileImageUrl!!)
            val columnModifier = Modifier
                .padding(start = 3.dp)
                .fillMaxWidth()
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
                AttachUserName(reviews.reviewer)
                AttachReviewDate(reviews.reviewDate!!)
            }

        }
    }

@Composable
fun AttachProductReviewHeader(reviews: ProductReview) {
    val rowModifier = Modifier
        .fillMaxWidth()
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = rowModifier
    ) {
        AttachUserImage(reviews.productReviewer?.profileImageUrl!!)
        val columnModifier = Modifier
            .padding(start = 3.dp)
            .fillMaxWidth()
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
            AttachProductReviewerUserName(reviews.productReviewer)
            AttachReviewDate(reviews.reviewDate!!)
        }

    }
}

@Composable
fun AttachUserName(reviewer: Reviewer){
    val rowModifier = Modifier
        .padding(start = 5.dp)
        .wrapContentWidth()

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TextComponent(
                text = reviewer.firstname+" "+reviewer.lastname?.first().toString()
                    .uppercase() +".",
                fontSize = 16,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


@Composable
fun AttachProductReviewerUserName(reviewer: User){
    val rowModifier = Modifier
        .padding(start = 5.dp)
        .wrapContentWidth()

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = rowModifier
    ) {
        TextComponent(
            text = reviewer.firstname+" "+reviewer.lastname?.first().toString()
                .uppercase() +".",
            fontSize = 16,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}



@Composable
fun AttachReviewDate(reviewDate: String){
    val rowModifier = Modifier
        .padding(start = 5.dp, top = 7.dp)
        .wrapContentWidth()

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TextComponent(
                text = reviewDate,
                fontSize = 14,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium
            )


        }
    }

@Composable
fun AttachUserReviewsContent(reviewText: String) {
    val columnModifier = Modifier
        .padding(bottom = 20.dp, top = 5.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        val modifier = Modifier
            .fillMaxWidth()
        TextComponent(
            textModifier = modifier, text = reviewText, fontSize = 16, fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6, textColor = Color.DarkGray, textAlign = TextAlign.Left,
            fontWeight = FontWeight.Medium, lineHeight = 25)

    }
}
