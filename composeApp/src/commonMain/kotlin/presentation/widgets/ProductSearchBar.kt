package presentation.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import presentation.widgets.SearchBarWidget

@Composable
fun SearchBar(placeholderText: String = "Search Products", onValueChange: (String) -> Unit, onBackPressed:() -> Unit){
    val searchIcon = remember { mutableStateOf("drawable/back_arrow.png") }
   SearchBarWidget(iconRes = searchIcon.value, placeholderText = placeholderText, iconSize = 26, onBackPressed = {
       searchIcon.value = "drawable/search_icon.png"
       onBackPressed()
   }){
        searchIcon.value = "drawable/back_arrow.png"
        onValueChange(it)
   }

}