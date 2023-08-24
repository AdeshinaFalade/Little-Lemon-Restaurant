package com.example.littlelemonrestaurant

interface Destinations {
    val route: String
}

object Onboarding : Destinations{
    override val route: String = "Onboarding"
}

object Home : Destinations{
    override val route: String = "Home"
}

object Profile : Destinations{
    override val route: String = "Profile"
}


