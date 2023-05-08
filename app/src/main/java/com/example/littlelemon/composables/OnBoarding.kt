package com.example.littlelemon.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.Colors
import com.example.littlelemon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoarding() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {

        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp),
            contentDescription = "Logo"
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(top = 16.dp),
            color = Colors.primaryGreen
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Let's get to know you",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                text = "Personal information",
                modifier = Modifier.padding(top = 40.dp, bottom = 32.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            label = { Text(text = "first name")},
            placeholder = { Text(text = "first name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            label = { Text(text = "last name")},
            placeholder = { Text(text = "last name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            label = { Text(text = "email")},
            placeholder = { Text(text = "email") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            singleLine = true
        )

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Colors.primaryYellow
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(0.5.dp, Color.Red)
            ) {
                Text(text = "Register", fontSize = 24.sp, color = Color.Black)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    OnBoarding()
}