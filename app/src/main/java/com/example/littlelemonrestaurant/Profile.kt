package com.example.littlelemonrestaurant

import android.content.Context
import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemonrestaurant.components.EMAIL
import com.example.littlelemonrestaurant.components.FIRST_NAME
import com.example.littlelemonrestaurant.components.LAST_NAME
import com.example.littlelemonrestaurant.components.LITTLE_LEMON
import com.example.littlelemonrestaurant.components.LOGIN_STATUS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navController: NavHostController
) {
    val context = LocalContext.current

    val sharedPref = remember {
        context.getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
    }
    val firstName = sharedPref?.getString(FIRST_NAME, "") ?: ""
    val lastName = sharedPref?.getString(LAST_NAME, "") ?: ""
    val email = sharedPref?.getString(EMAIL, "") ?: ""

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
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = "Personal Information",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 50.dp)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                },
                enabled = false,
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                },
                enabled = false,
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                enabled = false,
                onValueChange = {
                },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = {
                    sharedPref.edit().clear().apply()
                    navController.navigate(Onboarding.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Log Out", color = Color.Black)
            }
        }
    }
}