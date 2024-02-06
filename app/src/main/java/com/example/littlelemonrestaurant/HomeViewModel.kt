package com.example.littlelemonrestaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemonrestaurant.components.MENU_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    val loading = MutableStateFlow(false)
    val menuItems = appDatabase.menuItemDao().getAll()

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
    init {
        viewModelScope.launch(Dispatchers.IO) {
            loading.value = true
            if (appDatabase.menuItemDao().isEmpty()) {
                val menuItemsNetwork = fetchMenu()
                saveMenuToDatabase(menuItemsNetwork)
            }
            loading.value = false
        }
    }




//    private val httpClient = HttpClient {
//        install(ContentNegotiation) {
//            register(
//                ContentType.Text.Any ,
//                KotlinxSerializationConverter(Json {
//                    ignoreUnknownKeys = true
//                    prettyPrint = true
//                    isLenient = true
//
//                })
//            )
//        }
//        install(Logging) {
//            logger = Logger.DEFAULT
//            level = LogLevel.ALL
//        }
//
//    }

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

    fun deleteMenuItem(menuItem: MenuItemRoom) {
        viewModelScope.launch (Dispatchers.IO) {
            appDatabase.menuItemDao().deleteItem(menuItem)
        }
    }

    override fun onCleared() {
        httpClient.close()
    }
}

