package com.example.littlelemonrestaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetworkData(
    @SerialName("menu")
    val menu: List<Menu>
)

@Serializable
data class Menu(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: String,
    val title: String
) {
    fun toMenuItemRoom() = MenuItemRoom(
        id,
        title,
        price.toDouble(),
        category,
        description,
        image
    )
}