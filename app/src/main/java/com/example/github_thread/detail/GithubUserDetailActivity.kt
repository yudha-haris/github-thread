package com.example.github_thread.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.github_thread.R

class GithubUserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_detail)
    }

    @Preview
    @Composable
    fun DetailScreen() {
        MaterialTheme {
            Scaffold(
                backgroundColor = Color.Black,
                topBar = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    finish()
                                }
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Back Button",
                                tint = Color.White
                            )
                            Text("Back", color = Color.White)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column() {
                                Text(
                                    "Sidiq Permana",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight(600)
                                )
                                Text("sidiqpermana", color = Color.White, fontSize = 14.sp)
                            }
                            AsyncImage(
                                model = "https://avatars.githubusercontent.com/u/4090245?v=4",
                                contentScale = ContentScale.Crop,
                                contentDescription = "User Profile",
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Add to Favorite", fontWeight = FontWeight(600))

                        }
                    }
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    items(count = 20) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        ) {
                            Column() {
                                Text(
                                    "Flutter Boilerplate",
                                    fontWeight = FontWeight(600),
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "Flutter Project Boilerplate",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Dart", color = Color.LightGray, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(
                                    color = Color.Gray,
                                    thickness = (0.6).dp,
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}