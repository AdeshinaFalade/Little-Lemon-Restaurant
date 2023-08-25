package com.example.littlelemonrestaurant

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
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
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class HomeViewModel(menuItemDao: MenuItemDao) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState = _uiState.asStateFlow()

    val menuItemsRoom = menuItemDao.getAll()


    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
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

    init {
        if (menuItemDao.isEmpty()) {
            getMenuItems()
            val menuItemsRoom = _uiState.value.menuItems.map { it.toMenuItemRoom() }
            menuItemDao.insertAll(*menuItemsRoom.toTypedArray())
        }

    }

    private fun getMenuItems() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                val response: MenuNetworkData = httpClient.get(MENU_URL).body()
                println("http $response")
                _uiState.update {
                    it.copy(
                        menuItems = response.menu,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                println("http ${e.message}")
            }

        }
    }

    override fun onCleared() {
        httpClient.close()
    }
}

data class MenuUiState(
    val isLoading: Boolean = false,
    val menuItems: List<Menu> = emptyList()
)

