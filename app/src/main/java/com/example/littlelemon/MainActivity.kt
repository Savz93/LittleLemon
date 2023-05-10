package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import coil.compose.rememberImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.littlelemon.composables.OnBoarding
import com.example.littlelemon.composables.Profile
import com.example.littlelemon.data.MenuDatabase
import com.example.littlelemon.data.MenuItemRoom
import com.google.accompanist.glide.rememberGlidePainter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    
    lateinit var navHostController: NavHostController

    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    val database by lazy {
        Room.databaseBuilder(applicationContext, MenuDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            navHostController = rememberNavController()

            NavHost(
                navController = navHostController,
                startDestination =
                if (!context.getSharedPreferences("user", MODE_PRIVATE).contains(R.string.first_name.toString())) Destinations.OnBoardingScreen.route
                else Destinations.HomeScreen.route
            ) {
                composable(Destinations.OnBoardingScreen.route) { OnBoarding(navHostController) }
                composable(Destinations.HomeScreen.route) { Home(navHostController) }
                composable(Destinations.ProfileScreen.route) { Profile(navHostController) }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                saveMenuToDatabase(fetchMenu().menu)
            }
        }
    }

    private suspend fun fetchMenu(): MenuNetwork {
        return httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body()
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navHostController: NavHostController) {

    var search by remember { mutableStateOf("") }
    val dishCategories = listOf("Starters", "Mains", "Desserts", "Drinks")
    val context = LocalContext.current

    val database by lazy {
        Room.databaseBuilder(context, MenuDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }

    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    var isSortedByCategory by remember { mutableStateOf(false) }
    var sortByCategoryName by remember { mutableStateOf("") }
    var menuItems =
        if (search.isNotEmpty())
            databaseMenuItems.filter { it.title.contains(search, ignoreCase = true) }
        else if (isSortedByCategory)
            databaseMenuItems.filter {
                it.category.lowercase() == sortByCategoryName.lowercase()
            }
        else
            databaseMenuItems

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 16.dp, end = 32.dp)
                    .width(200.dp),
                contentDescription = "Logo"
            )

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp)
                    .size(50.dp)
                    .clickable { navHostController.navigate(Destinations.ProfileScreen.route) },
                contentDescription = "Logo"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .background(Colors.primaryGreen)
                .height(300.dp)
        ) {
            Row(modifier = Modifier.height(200.dp)) {
                Column() {
                    Text(
                        modifier = Modifier.padding(start = 24.dp, top = 24.dp),
                        text = stringResource(id = R.string.little_lemon),
                        fontSize = 40.sp,
                        color = Colors.primaryYellow
                    )
                    Text(
                        text = stringResource(id = R.string.chicago),
                        color = Color.White,
                        modifier = Modifier.padding(start = 24.dp),
                        fontSize = 24.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 24.dp)
                            .width(220.dp),
                        text = stringResource(id = R.string.description_one_hero_section),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(top = 80.dp, end = 24.dp, start = 20.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentDescription = "Logo"
                )
            }

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 30.dp, bottom = 12.dp)
                    .fillMaxHeight()
                    .height(50.dp)
                ,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.White
                ),
                label = { Text(text = "search")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                },
                singleLine = true
            )
        }

        Column {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 30.dp)
            ) {
                Text(
                    text = "ORDER FOR DELIVERY!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp)
            }
            LazyRow(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dishCategories) { dishCategory ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .width(80.dp)
                            .height(36.dp)
                            .clickable {
                                isSortedByCategory = true
                                sortByCategoryName = dishCategory
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = dishCategory,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Colors.primaryGreen
                            )
                        }
                    }
                }
            }

            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 24.dp, end = 16.dp)
            )
        }



        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            items(
                items = if(search.isNotEmpty()) menuItems else menuItems.filter { it.title.contains(search, ignoreCase = true) },
                itemContent = { menuItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column(modifier = Modifier) {
                            Text(
                                modifier = Modifier.padding(start = 24.dp, bottom = 16.dp, top = 16.dp),
                                text = menuItem.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(0.65f)
                                    .padding(start = 24.dp, bottom = 12.dp),
                                text = menuItem.description,
                                color = Colors.primaryGreen,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "$${menuItem.price}.00" ,
                                modifier = Modifier.padding(bottom = 12.dp, start = 24.dp),
                                color = Colors.primaryGreen,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Image(
                            painter = rememberGlidePainter(
                                request = menuItem.image,
                                previewPlaceholder = R.drawable.greek_salad
                            ),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(end = 24.dp, start = 16.dp, top = 12.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentDescription = "Food Image"
                        )
                    }

                    Divider(modifier = Modifier
                        .padding(horizontal = 24.dp)
                    )
                }
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(rememberNavController())
}


