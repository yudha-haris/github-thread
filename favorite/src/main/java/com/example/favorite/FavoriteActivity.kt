package com.example.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.component.CircularLoading
import com.example.component.SearchBar
import com.example.component.UserItemCard
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

@Composable
fun FavoriteScreen(
    users: State<Resource<List<GithubUser>>>,
    onItemTap: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onKeyboardDone: (String) -> Unit,
){
    MaterialTheme {
        Scaffold(
            backgroundColor = Color.Black,
            topBar = {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Favorite User",
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight(800),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                        )
                    ) {
                        SearchBar(
                            onValueChange,
                            onKeyboardDone,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        ) { innerPadding ->
            users.value.let {
                when(it){
                    is Resource.Success -> {
                        LazyColumn(
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            items(10) { idx ->
                                UserItemCard(it.data!![idx], onTap = {username ->
                                    onItemTap(username)
                                })
                            }
                        }
                    }
                    is Resource.Loading -> {
                        CircularLoading()
                    }
                    else -> {}
                }
            }

        }
    }
}