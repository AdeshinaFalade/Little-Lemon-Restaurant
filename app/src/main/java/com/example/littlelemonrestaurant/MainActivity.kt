package com.example.littlelemonrestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemonrestaurant.components.MENU_URL
import com.example.littlelemonrestaurant.ui.theme.LittleLemonRestaurantTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val appDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            LittleLemonRestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val databaseMenuItems by appDatabase.menuItemDao().getAll().observeAsState(emptyList())
                    val navController = rememberNavController()
                    MyNavigation(navController = navController, menuItems = databaseMenuItems)
                }
            }
            lifecycleScope.launch(Dispatchers.IO) {
                if (appDatabase.menuItemDao().isEmpty()) {
                    val menuItemsNetwork = fetchMenu()
                    saveMenuToDatabase(menuItemsNetwork)
                }
            }

        }
    }

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private suspend fun fetchMenu(): List<Menu> {
        return httpClient
            .get(MENU_URL)
            .body<MenuNetworkData>()
            .menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<Menu>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        appDatabase.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }


}