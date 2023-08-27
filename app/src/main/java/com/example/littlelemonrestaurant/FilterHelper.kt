package com.example.littlelemonrestaurant

class FilterHelper {
    fun filterMenu(type: FilterType, menuItems: List<MenuItemRoom>): List<MenuItemRoom>{
        return when(type){
            FilterType.All -> menuItems
            FilterType.Dessert -> menuItems.filter { it.category == "desserts" }
            FilterType.Mains -> menuItems.filter { it.category == "mains" }
            FilterType.Starters -> menuItems.filter { it.category == "starters" }
        }
    }
}