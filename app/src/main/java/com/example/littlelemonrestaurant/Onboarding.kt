package com.example.littlelemonrestaurant

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemonrestaurant.components.EMAIL
import com.example.littlelemonrestaurant.components.FIRST_NAME
import com.example.littlelemonrestaurant.components.LAST_NAME
import com.example.littlelemonrestaurant.components.LITTLE_LEMON
import com.example.littlelemonrestaurant.components.LOGIN_STATUS
import com.example.littlelemonrestaurant.ui.theme.Karla
import com.example.littlelemonrestaurant.ui.theme.Markazi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(
    navController: NavHostController
) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val sharedPref = remember {
        context.getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
    }

    BackHandler(true) {
        (context as ComponentActivity).finish()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(height = 80.dp, width = 185.dp)
                    .padding(vertical = 20.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = Color(0xFF495E57)), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Let's get to know you",
                    fontSize = 25.sp,
                    fontFamily = Karla,
                    color = Color.White,
                    fontWeight = FontWeight.W400
                )
            }
            Text(
                text = "Personal Information",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = Markazi,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 50.dp)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()
                    ) {
                        Toast.makeText(
                            context,
                            "Registration unsuccessful. Please enter all data.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    ) {
                        Toast.makeText(
                            context,
                            "Invalid email format",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        sharedPref.edit().putBoolean(LOGIN_STATUS, true).apply()
                        sharedPref.edit().putString(FIRST_NAME, firstName).apply()
                        sharedPref.edit().putString(LAST_NAME, lastName).apply()
                        sharedPref.edit().putString(EMAIL, email).apply()

                        navController.navigate(Home.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Register", color = Color.Black)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun previewOnboarding() {
    Onboarding(rememberNavController())
}