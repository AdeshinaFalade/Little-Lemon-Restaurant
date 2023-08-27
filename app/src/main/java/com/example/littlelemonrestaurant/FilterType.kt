package com.example.littlelemonrestaurant

sealed class FilterType(val name: String) {
    object All : FilterType("All")
    object Mains : FilterType("Mains")
    object Starters : FilterType("Starters")
    object Dessert : FilterType("Desserts")
}