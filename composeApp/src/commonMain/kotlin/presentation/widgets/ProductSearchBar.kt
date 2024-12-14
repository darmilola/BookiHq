package presentation.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import presentation.widgets.SearchBarWidget

@Composable
fun SearchBar(placeholderText: String = "Search Products",searchIcon: String = "drawable/back_arrow.png",  onValueChange: (String) -> Unit, onBackPressed:() -> Unit){
   SearchBarWidget(iconRes = searchIcon, placeholderText = placeholderText, iconSize = 26, onBackPressed = {
       onBackPressed()
   }){
       onValueChange(it)
   }

}