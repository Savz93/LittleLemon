package com.example.littlelemon.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.Colors
import com.example.littlelemon.Destinations
import com.example.littlelemon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        val context = LocalContext.current
        val myPrefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)

        val firstName = myPrefs.getString(R.string.first_name.toString(), "John")
        val lastName = myPrefs.getString(R.string.last_name.toString(), "Doe")
        val email = myPrefs.getString(R.string.email.toString(), "JohnDoe@gmail.com")

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp),
            contentDescription = "Logo"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start) {

            Text(
                text = "Profile information",
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 80.dp, start = 24.dp)
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp),
            value = firstName!!,
            onValueChange = {},
            label = { Text(text = "First name") },
            placeholder = { Text(text = "First name") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp),
            value = lastName!!,
            onValueChange = {},
            label = { Text(text = "Last name") },
            placeholder = { Text(text = "Last name") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp),
            value = email!!,
            onValueChange = {},
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Email") }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    myPrefs.edit().clear().apply()

                    Toast.makeText(context, "You have logged out", Toast.LENGTH_SHORT).show()

                    navController.navigate(Destinations.OnBoardingScreen.route)
                },
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Colors.primaryYellow
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(0.5.dp, Color.Red)
            ) {
                Text(text = "Log out", fontSize = 24.sp, color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    Profile(rememberNavController())
}