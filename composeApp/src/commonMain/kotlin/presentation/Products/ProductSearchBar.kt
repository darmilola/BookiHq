package presentation.Products

import androidx.compose.runtime.Composable
import presentation.widgets.SearchBarWidget

@Composable
fun SearchBar(placeholderText: String = "Search Products", onValueChange: (String) -> Unit){

   SearchBarWidget(iconRes = "drawable/search_icon.png", placeholderText = placeholderText, iconSize = 26){
        onValueChange(it)
   }

}

