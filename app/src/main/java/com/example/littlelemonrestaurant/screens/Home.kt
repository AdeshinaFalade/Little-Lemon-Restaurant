package com.example.littlelemonrestaurant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemonrestaurant.FilterHelper
import com.example.littlelemonrestaurant.FilterType
import com.example.littlelemonrestaurant.HomeViewModel
import com.example.littlelemonrestaurant.MenuItemRoom
import com.example.littlelemonrestaurant.R
import com.example.littlelemonrestaurant.components.DisplayLoader
import com.example.littlelemonrestaurant.ui.theme.DeepGreen
import com.example.littlelemonrestaurant.ui.theme.Karla
import com.example.littlelemonrestaurant.ui.theme.Markazi
import com.example.littlelemonrestaurant.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavHostController, viewModel: HomeViewModel
) {
    var selectedFilterType by remember {
        mutableStateOf<FilterType>(FilterType.All)
    }

    val menuItems by viewModel.menuItems.observeAsState(emptyList())

    var filteredMenu by remember {
        mutableStateOf(menuItems)
    }

    filteredMenu = FilterHelper().filterMenu(selectedFilterType, menuItems)

    val loading by viewModel.loading.collectAsState()

    var searchText by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(height = 80.dp, width = 185.dp)
                        .padding(vertical = 20.dp)
                        .align(Alignment.Center)
                )
                Image(painter = painterResource(id = R.drawable.profile),
                    contentDescription = "profile",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterEnd)
                        .size(75.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate(com.example.littlelemonrestaurant.Profile.route)
                        })
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .background(color = DeepGreen)
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.75f)
                        .fillMaxWidth(0.5f)
                        .align(Alignment.TopStart), verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Little Lemon", style = TextStyle(
                            fontSize = 40.sp,
                            fontFamily = Markazi,
                            fontWeight = FontWeight(700),
                            color = Yellow,
                        )
                    )
                    Text(
                        text = "Chicago", style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = Markazi,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFEDEFEE),
                        ), modifier = Modifier.offset(y = (-15).dp)
                    )
                    Text(
                        text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = Karla,
                            fontWeight = FontWeight(400),
                            color = Color.White,
                        ),
                        modifier = Modifier.offset(y = (-10).dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.hero_mage),
                    contentDescription = "hero",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(height = 152.dp, width = 143.dp)
                        .padding(bottom = 16.dp)
                )
                TextField(value = searchText, onValueChange = {
                    searchText = it
                }, placeholder = {
                    Text(text = "Enter search phrase")
                }, leadingIcon = {
                    Image(
                        imageVector = Icons.Default.Search, contentDescription = "search icon"
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)

                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "ORDER FOR DELIVERY!", style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = Markazi,
                    fontWeight = FontWeight(700),
                    color = Color.Black
                ), modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                FilterChip(selected = selectedFilterType.name == "All", onClick = {
                    selectedFilterType = FilterType.All
                }, label = {
                    Text(
                        text = "All", style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = Karla,
                            fontWeight = FontWeight(700),
                            color = Color.Black,
                        )
                    )
                })
                FilterChip(selected = selectedFilterType.name == "Starters", onClick = {
                    selectedFilterType = FilterType.Starters
                }, label = {
                    Text(
                        text = "Starters", style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = Karla,
                            fontWeight = FontWeight(700),
                            color = Color.Black,
                        )
                    )
                })
                FilterChip(selected = selectedFilterType.name == "Desserts",
                    onClick = { selectedFilterType = FilterType.Dessert },
                    label = {
                        Text(
                            text = "Desserts", style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = Karla,
                                fontWeight = FontWeight(700),
                                color = Color.Black,
                            )
                        )
                    })
                FilterChip(selected = selectedFilterType.name == "Mains",
                    onClick = { selectedFilterType = FilterType.Mains },
                    label = {
                        Text(
                            text = "Mains", style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = Karla,
                                fontWeight = FontWeight(700),
                                color = Color.Black,
                            )
                        )
                    })
            }
            val searchedItems =
                filteredMenu.filter { it.title.contains(searchText.trim(), ignoreCase = true) }
            MenuItemsList(searchedItems)
        }

        if (loading) {
            DisplayLoader()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp)
    ) {
        items(items = items, itemContent = { menuItem ->
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                thickness = 1.dp,
                color = Color(0xFF444444)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(0.75f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = menuItem.title, style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = Markazi,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = menuItem.description, style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = Karla,
                            fontWeight = FontWeight(300),
                            color = Color(0xFF444444),
                        ), maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "$${menuItem.price}", style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = Karla,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF444444),
                        )
                    )
                }
                GlideImage(
                    model = menuItem.image,
                    contentDescription = menuItem.title,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(0.25f)
                        .size(60.dp)
                        .clip(RectangleShape)
                )
            }
        })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun PrevHome() {

}
