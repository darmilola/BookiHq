package presentation.account

import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.Settings
import domain.Models.FavoriteProductIdModel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import presentation.consultation.rightTopBarItem
import presentation.viewmodels.MainViewModel
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget
import rememberStackedSnackbarHostState
import utils.ParcelableScreen

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class FavoriteProducts() : ParcelableScreen, KoinComponent, Parcelable, ScreenTransition {

    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    val preferenceSettings = Settings()
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null
    @Transient var favoriteProductIdModelList = listOf<FavoriteProductIdModel>()

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }


    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        runBlocking {
            favoriteProductIdModelList = databaseBuilder!!.build().getFavoriteProductDao().getAllFavoriteProduct()
        }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )
        val navigator = LocalNavigator.currentOrThrow
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }



        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                FavoriteProductScreenTopBar(onBackPressed = {
                    navigator.pop()
                })
            },
            content = {

            })
    }


    @Composable
    fun FavoriteProductScreenTopBar(onBackPressed: () -> Unit) {

        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp)
            .height(40.dp)

        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem(onBackPressed = {
                    onBackPressed()
                })
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                FavoriteProductTitle()
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                rightTopBarItem()
            }
        }
    }
    @Composable
    fun FavoriteProductTitle(){
        TitleWidget(textColor = theme.styles.Colors.primaryColor, title = "Favorite Products")
    }

    @Composable
    fun leftTopBarItem(onBackPressed:() -> Unit) {
        PageBackNavWidget() {
            onBackPressed()
        }
    }

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }


}
