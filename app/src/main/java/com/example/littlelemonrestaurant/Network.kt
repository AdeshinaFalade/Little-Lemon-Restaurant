package com.example.littlelemonrestaurant

import kotlinx.serialization.Serializable

@Serializable
data class MenuNetworkData(
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
)